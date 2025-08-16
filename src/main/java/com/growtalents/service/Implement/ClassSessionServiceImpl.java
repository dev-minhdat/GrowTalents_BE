package com.growtalents.service.Implement;

import com.growtalents.dto.request.ClassSession.ClassSessionCreateRequestDTO;
import com.growtalents.dto.request.ClassSession.ClassSessionRescheduleRequestDTO;
import com.growtalents.dto.response.ClassSession.ClassSessionResponseDTO;
import com.growtalents.enums.RescheduleStatus;
import com.growtalents.exception.BadRequestException;
import com.growtalents.exception.ResourceNotFoundException;
import com.growtalents.helper.IdGenerator;
import com.growtalents.mapper.ClassSessionMapper;
import com.growtalents.model.ClassSession;
import com.growtalents.model.Course;
import com.growtalents.repository.ClassSessionRepository;
import com.growtalents.repository.CourseRepository;
import com.growtalents.repository.TeacherCourseRepository;
import com.growtalents.service.Interfaces.ClassSessionService;
import com.growtalents.service.Interfaces.NotificationService;
import com.growtalents.service.id.IdSequenceService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ClassSessionServiceImpl implements ClassSessionService {

    private final ClassSessionRepository classSessionRepository;
    private final CourseRepository courseRepository;
    private final TeacherCourseRepository teacherCourseRepository;
    private final ClassSessionMapper classSessionMapper;
    private final NotificationService notificationService;
    private final IdSequenceService idSequenceService;

    @Transactional
    @Override
    public void requestReschedule(String teacherId, ClassSessionRescheduleRequestDTO dto) {
        ClassSession cs = classSessionRepository.findById(dto.getSessionId())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy session: " + dto.getSessionId()));

        // Chỉ cho đề xuất 1 lần
        if (cs.getRescheduleStatus() != null) {
            throw new IllegalStateException("Buổi học này đã có đề xuất dời lịch trước đó.");
        }

        if (dto.getProposedDate() == null || dto.getProposedStartTime() == null) {
            throw new IllegalArgumentException("Thiếu ngày/giờ đề xuất.");
        }

        // BE tự tính end từ duration
        LocalTime proposedEnd = dto.getProposedStartTime().plusMinutes(cs.getDurationInMinutes());

        // (Optional) không cho qua ngày – nếu end < start
        if (proposedEnd.isBefore(dto.getProposedStartTime())) {
            throw new IllegalArgumentException("Khung giờ không hợp lệ (qua ngày).");
        }

        // Check trùng slot với buổi khác cùng giáo viên
        boolean conflict = classSessionRepository.existsTimeConflict(
                teacherId,
                dto.getProposedDate(),
                dto.getProposedStartTime(),
                proposedEnd,
                cs.getSessionId()
        );
        if (conflict) {
            throw new IllegalStateException("Trùng khung giờ với buổi dạy khác.");
        }

        // Set đề xuất
        cs.setProposedDate(dto.getProposedDate());
        cs.setProposedStartTime(dto.getProposedStartTime());
        cs.setRescheduleReason(dto.getRescheduleReason());
        cs.setRescheduleStatus(RescheduleStatus.PENDING);
        cs.setRescheduleSubmittedAt(LocalDateTime.now());
        classSessionRepository.save(cs);
    }

    @Transactional
    @Override
    public void approveReschedule(String sessionId) {
        ClassSession cs = classSessionRepository.findById(sessionId)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy session: " + sessionId));

        if (cs.getRescheduleStatus() != RescheduleStatus.PENDING) {
            throw new IllegalStateException("Chỉ duyệt khi đang ở trạng thái PENDING.");
        }
        if (cs.getProposedDate() == null || cs.getProposedStartTime() == null) {
            throw new IllegalStateException("Đề xuất không hợp lệ: thiếu ngày/giờ.");
        }

        // Giữ bản cũ để tạo summary thay đổi
        ClassSession before = ClassSession.builder()
                .sessionId(cs.getSessionId())
                .sessionDate(cs.getSessionDate())
                .startTime(cs.getStartTime())
                .endTime(cs.getEndTime())
                .topic(cs.getTopic())
                .course(cs.getCourse())
                .durationInMinutes(cs.getDurationInMinutes())
                .build();

        // Gán originalDate nếu chưa có
        if (cs.getOriginalDate() == null) {
            cs.setOriginalDate(cs.getSessionDate());
        }

        // Cập nhật lịch THỰC TẾ theo đề xuất (end = start + duration)
        cs.setSessionDate(cs.getProposedDate());
        cs.setStartTime(cs.getProposedStartTime());
        cs.setEndTime(cs.getProposedStartTime().plusMinutes(cs.getDurationInMinutes()));
        cs.setRescheduleStatus(RescheduleStatus.APPROVED);

        // (tuỳ chọn) clear đề xuất sau khi áp dụng
        // cs.setProposedDate(null);
        // cs.setProposedStartTime(null);
        // cs.setRescheduleReason(null);

        classSessionRepository.save(cs);

        // Tạo notification cho tất cả học sinh ENROLLED
        String summary = buildChangeSummary(before, cs);
        notificationService.createScheduleChangeForCourseAndStudents(
                cs.getCourse().getCourseId(),
                summary
        );
    }

    @Transactional
    @Override
    public void rejectReschedule(String sessionId, String reason) {
        ClassSession cs = classSessionRepository.findById(sessionId)
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy session: " + sessionId));

        if (cs.getRescheduleStatus() != RescheduleStatus.PENDING) {
            throw new IllegalStateException("Chỉ từ chối khi đang ở trạng thái PENDING.");
        }

        cs.setRescheduleStatus(RescheduleStatus.REJECTED);
        // Nếu muốn lưu lý do từ chối riêng, có thể tái sử dụng rescheduleReason hoặc thêm field mới trong tương lai
        if (reason != null && !reason.isBlank()) {
            cs.setRescheduleReason((cs.getRescheduleReason() == null ? "" : cs.getRescheduleReason() + " | ")
                    + "Reject: " + reason);
        }

        classSessionRepository.save(cs);
    }

    @Override
    @Transactional
    public List<ClassSessionResponseDTO> getAllRescheduled(String teacherId) {
        var sessions = classSessionRepository.findReschedulesByTeacher(teacherId);
        return classSessionMapper.toResponseDTO(sessions);
    }

    @Transactional
    @Override
    public void createClassSession(String teacherId, ClassSessionCreateRequestDTO dto) {
        // Validate course tồn tại
        Course course = courseRepository.findById(dto.getCourseId())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy khóa học với ID: " + dto.getCourseId()));
        
        // Validate giáo viên có quyền tạo buổi học cho khóa này không
        if (!teacherCourseRepository.existsActiveAssignment(teacherId, dto.getCourseId())) {
            throw new BadRequestException("Giáo viên không có quyền tạo buổi học cho khóa học này");
        }
        
        // Validate thời gian
        if (dto.getStartTime() == null || dto.getSessionDate() == null) {
            throw new BadRequestException("Ngày học và thời gian bắt đầu không được để trống");
        }
        
        // Validate ngày học không được trong quá khứ
        if (dto.getSessionDate().isBefore(java.time.LocalDate.now())) {
            throw new BadRequestException("Không thể tạo buổi học trong quá khứ");
        }
        
        if (dto.getDurationInMinutes() <= 0) {
            throw new BadRequestException("Thời lượng buổi học phải lớn hơn 0");
        }
        
        // Validate thời lượng hợp lý (không quá 8 giờ)
        if (dto.getDurationInMinutes() > 480) {
            throw new BadRequestException("Thời lượng buổi học không được vượt quá 8 giờ");
        }
        
        // Validate giờ dạy hợp lý (từ 6:00 đến 22:00)
        if (dto.getStartTime().isBefore(java.time.LocalTime.of(6, 0)) || 
            dto.getStartTime().isAfter(java.time.LocalTime.of(22, 0))) {
            throw new BadRequestException("Giờ bắt đầu phải trong khoảng 6:00 - 22:00");
        }
        
        // Tự động tính endTime từ startTime + duration
        LocalTime endTime = dto.getStartTime().plusMinutes(dto.getDurationInMinutes());
        
        // Validate không qua ngày (endTime không nhỏ hơn startTime do overflow)
        if (endTime.isBefore(dto.getStartTime())) {
            throw new BadRequestException("Buổi học không thể kéo dài qua nửa đêm");
        }
        
        // Validate không có xung đột thời gian với các buổi học khác
        if (classSessionRepository.existsTimeConflictForNewSession(
                teacherId, dto.getSessionDate(), dto.getStartTime(), endTime)) {
            throw new BadRequestException("Thời gian này đã có buổi học khác. Vui lòng chọn thời gian khác");
        }
        
        // Tạo ID mới
        int nextSequence = idSequenceService.getNextIndex("ClassSession");
        String sessionId = IdGenerator.generateClassSessionId(nextSequence);
        
        // Tạo buổi học mới
        ClassSession classSession = ClassSession.builder()
                .sessionId(sessionId)
                .originalDate(dto.getSessionDate())
                .sessionDate(dto.getSessionDate())
                .topic(dto.getTopic())
                .durationInMinutes(dto.getDurationInMinutes())
                .startTime(dto.getStartTime())
                .endTime(endTime)  // Sử dụng endTime đã tính
                .course(course)
                .build();
        
        classSessionRepository.save(classSession);
    }

    @Override
    public List<ClassSessionResponseDTO> getTeacherClassSessions(String teacherId) {
        List<ClassSession> classSessions = classSessionRepository.findByTeacherId(teacherId);
        return classSessions.stream()
                .map(classSessionMapper::toResponseDTO)
                .toList();
    }

    // ================== helpers ==================

    private String buildChangeSummary(ClassSession before, ClassSession after) {
        String topic = (after.getTopic() != null && !after.getTopic().isBlank())
                ? after.getTopic() : "(Không tiêu đề)";

        String oldStr = String.format("%s %s–%s",
                safeDate(before.getSessionDate()),
                safeTime(before.getStartTime()),
                safeTime(before.getEndTime()));

        String newStr = String.format("%s %s–%s",
                safeDate(after.getSessionDate()),
                safeTime(after.getStartTime()),
                safeTime(after.getEndTime()));

        return "Buổi học \"" + topic + "\" đổi từ " + oldStr + " → " + newStr;
    }

    private String safeDate(java.time.LocalDate d) {
        return d == null ? "?" : d.toString();
    }

    private String safeTime(java.time.LocalTime t) {
        return t == null ? "?" : t.toString();
    }
}
