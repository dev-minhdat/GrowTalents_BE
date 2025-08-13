package com.growtalents.service.Implement;

import com.growtalents.repository.*;
import com.growtalents.model.*;
import com.growtalents.enums.AttendanceStatus;
import com.growtalents.helper.IdGenerator;
import com.growtalents.service.Interfaces.AttendanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class AttendanceServiceImpl implements AttendanceService {

    private final AttendanceRepository attendanceRepository;
    private final TeacherCourseRepository teacherCourseRepository;
    private final ClassSessionRepository classSessionRepository;
    private final CourseRepository courseRepository;
    private final AppUserRepository appUserRepository;
    @Override
    public List<Map<String, Object>> getTeacherClasses(String teacherId) {
        try {
            // Tìm các khóa học mà giáo viên đang dạy
            List<TeacherCourse> teacherCourses = teacherCourseRepository.findByTeacherUserId(teacherId);

            List<Map<String, Object>> result = new ArrayList<>();

            for (TeacherCourse tc : teacherCourses) {
                Course course = tc.getCourse();

                Map<String, Object> courseInfo = new HashMap<>();
                courseInfo.put("courseId", course.getCourseId());
                courseInfo.put("courseName", course.getName());
                courseInfo.put("courseType", course.getType().toString());
                courseInfo.put("studentCount", 25); // Mock data
                courseInfo.put("totalSessions", 30);
                courseInfo.put("completedSessions", 12);

                result.add(courseInfo);
            }

            return result;
        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi lấy danh sách lớp của giáo viên: " + e.getMessage());
        }
    }

    @Override
    public String createAttendance(Map<String, Object> request) {
        try {
            String courseId = (String) request.get("courseId");
            String sessionId = (String) request.get("sessionId");
            String attendanceDateStr = (String) request.get("attendanceDate");
            List<Map<String, Object>> attendanceRecords = (List<Map<String, Object>>) request.get("attendanceRecords");

            LocalDate attendanceDate = LocalDate.parse(attendanceDateStr);

            // Kiểm tra buổi học có tồn tại không
            ClassSession session = classSessionRepository.findById(sessionId)
                    .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy buổi học"));

            int createdCount = 0;

            for (Map<String, Object> record : attendanceRecords) {
                String studentId = (String) record.get("studentId");
                String statusStr = (String) record.get("status");
                String note = (String) record.get("note");

                // Kiểm tra điểm danh đã tồn tại chưa
                boolean exists = attendanceRepository.existsBySessionIdAndStudentId(sessionId, studentId);
                if (exists) {
                    continue; // Bỏ qua nếu đã có điểm danh
                }

                // Tạo điểm danh mới
                Attendance attendance = new Attendance();
                attendance.setAttendanceId(IdGenerator.generateUUID());
                attendance.setSession(session);
                attendance.setStudent(appUserRepository.findById(studentId)
                        .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy học sinh")));
                attendance.setStatus(AttendanceStatus.valueOf(statusStr));
                attendance.setNote(note);
                attendance.setAttendanceDate(attendanceDate);

                attendanceRepository.save(attendance);
                createdCount++;
            }

            return String.format("Đã tạo điểm danh thành công cho %d học sinh", createdCount);

        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi tạo điểm danh: " + e.getMessage());
        }
    }

    @Override
    public String updateAttendance(String attendanceId, Map<String, Object> request) {
        try {
            // Tìm điểm danh cần cập nhật
            Attendance attendance = attendanceRepository.findById(attendanceId)
                    .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy điểm danh"));

            // Cập nhật thông tin
            String statusStr = (String) request.get("status");
            String note = (String) request.get("note");

            if (statusStr != null) {
                attendance.setStatus(AttendanceStatus.valueOf(statusStr));
            }
            if (note != null) {
                attendance.setNote(note);
            }

            attendanceRepository.save(attendance);

            return "Cập nhật điểm danh thành công";

        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi cập nhật điểm danh: " + e.getMessage());
        }
    }

    @Override
    public List<Map<String, Object>> getAttendanceHistory(String teacherId, LocalDate startDate, LocalDate endDate) {
        try {
            // Nếu không có ngày bắt đầu/kết thúc, mặc định lấy 30 ngày gần nhất
            if (startDate == null) {
                startDate = LocalDate.now().minusDays(30);
            }
            if (endDate == null) {
                endDate = LocalDate.now();
            }

            // Mock data for now - trong thực tế sẽ query từ DB
            List<Map<String, Object>> result = new ArrayList<>();

            Map<String, Object> historyItem = new HashMap<>();
            historyItem.put("sessionDate", LocalDate.now());
            historyItem.put("courseId", "COURSE001");
            historyItem.put("courseName", "Java Programming");
            historyItem.put("presentCount", 20);
            historyItem.put("absentCount", 3);
            historyItem.put("lateCount", 2);
            historyItem.put("totalStudents", 25);

            result.add(historyItem);

            return result;

        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi lấy lịch sử điểm danh: " + e.getMessage());
        }
    }

    @Override
    public List<Map<String, Object>> getAttendanceBySession(String sessionId) {
        try {
            List<Attendance> attendances = attendanceRepository.findBySessionId(sessionId);

            List<Map<String, Object>> result = new ArrayList<>();

            for (Attendance attendance : attendances) {
                Map<String, Object> attendanceInfo = new HashMap<>();
                attendanceInfo.put("attendanceId", attendance.getAttendanceId());
                attendanceInfo.put("studentId", attendance.getStudent().getUserId());
                attendanceInfo.put("studentName", attendance.getStudent().getUserName());
                attendanceInfo.put("status", attendance.getStatus().toString());
                attendanceInfo.put("note", attendance.getNote());
                attendanceInfo.put("attendanceDate", attendance.getAttendanceDate());

                result.add(attendanceInfo);
            }

            return result;

        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi lấy điểm danh theo buổi học: " + e.getMessage());
        }
    }

    @Override
    public List<Map<String, Object>> getAttendanceByCourse(String courseId, LocalDate date) {
        try {
            List<Attendance> attendances = attendanceRepository.findByCourseIdAndDate(courseId, LocalDate.parse(String.valueOf(date)));

            List<Map<String, Object>> result = new ArrayList<>();

            for (Attendance attendance : attendances) {
                Map<String, Object> attendanceInfo = new HashMap<>();
                attendanceInfo.put("attendanceId", attendance.getAttendanceId());
                attendanceInfo.put("studentId", attendance.getStudent().getUserId());
                attendanceInfo.put("studentName", attendance.getStudent().getUserName());
                attendanceInfo.put("status", attendance.getStatus().toString());
                attendanceInfo.put("note", attendance.getNote());
                attendanceInfo.put("sessionTopic", attendance.getSession().getTopic());

                result.add(attendanceInfo);
            }

            return result;

        } catch (Exception e) {
            throw new RuntimeException("Lỗi khi lấy điểm danh theo khóa học: " + e.getMessage());
        }
    }
}
