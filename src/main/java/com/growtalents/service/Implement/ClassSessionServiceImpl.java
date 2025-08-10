package com.growtalents.service.Implement;

import com.growtalents.dto.request.ClassSession.ClassSessionRescheduleRequestDTO;
import com.growtalents.enums.RescheduleStatus;
import com.growtalents.exception.ResourceNotFoundException;
import com.growtalents.model.ClassSession;
import com.growtalents.repository.ClassSessionRepository;
import com.growtalents.service.Interfaces.ClassSessionService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class ClassSessionServiceImpl implements ClassSessionService {

    private final ClassSessionRepository classSessionRepository;

    @Transactional
    @Override
    public void requestReschedule(String teacherId, ClassSessionRescheduleRequestDTO dto) {
        ClassSession cs = classSessionRepository.findById(dto.getSessionId())
                .orElseThrow(() -> new ResourceNotFoundException("Không tìm thấy session: " + dto.getSessionId()));

        // Chỉ cho đề xuất 1 lần
        if (cs.getRescheduleStatus() != null) {
            throw new IllegalStateException("Buổi học này đã có đề xuất dời lịch trước đó.");
        }

        // BE tự tính end từ duration
        LocalTime proposedEnd = dto.getProposedStartTime().plusMinutes(cs.getDurationInMinutes());

        // (Optional) không cho qua ngày – tuỳ rule của em
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

        // Gán originalDate nếu chưa có
        if (cs.getOriginalDate() == null) {
            cs.setOriginalDate(cs.getSessionDate());
        }

        // Cập nhật lịch THỰC TẾ theo đề xuất (end = start + duration)
        cs.setSessionDate(cs.getProposedDate());
        cs.setStartTime(cs.getProposedStartTime());
        cs.setEndTime(cs.getProposedStartTime().plusMinutes(cs.getDurationInMinutes()));
        cs.setRescheduleStatus(RescheduleStatus.APPROVED);

        classSessionRepository.save(cs);
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
}
