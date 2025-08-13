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
            System.out.println("=== DEBUG CREATE ATTENDANCE ===");
            System.out.println("Full request: " + request);

            String courseId = (String) request.get("courseId");
            String sessionId = (String) request.get("sessionId");
            String attendanceDateStr = (String) request.get("attendanceDate");
            List<Map<String, Object>> attendanceRecords = (List<Map<String, Object>>) request.get("attendanceRecords");

            System.out.println("courseId: " + courseId);
            System.out.println("sessionId: " + sessionId);
            System.out.println("attendanceDate: " + attendanceDateStr);
            System.out.println("attendanceRecords: " + attendanceRecords);
            System.out.println("attendanceRecords size: " + (attendanceRecords != null ? attendanceRecords.size() : "null"));

            // ✅ KIỂM TRA REQUIRED FIELDS
            if (courseId == null || courseId.trim().isEmpty()) {
                throw new IllegalArgumentException("Course ID không được để trống");
            }
            if (sessionId == null || sessionId.trim().isEmpty()) {
                throw new IllegalArgumentException("Session ID không được để trống");
            }
            if (attendanceDateStr == null || attendanceDateStr.trim().isEmpty()) {
                throw new IllegalArgumentException("Attendance Date không được để trống");
            }

            LocalDate attendanceDate = LocalDate.parse(attendanceDateStr);

            // Kiểm tra buổi học có tồn tại không
            ClassSession session = classSessionRepository.findById(sessionId)
                    .orElseThrow(() -> new IllegalArgumentException("Không tìm thấy buổi học với ID: " + sessionId));

            System.out.println("Found session: " + session.getSessionId() + " - " + session.getTopic());

            int createdCount = 0;
            int skippedCount = 0;
            int errorCount = 0;

            // ✅ KIỂM TRA ATTENDANCE RECORDS
            if (attendanceRecords == null || attendanceRecords.isEmpty()) {
                System.out.println("No attendance records provided - creating empty session marker");

                // ✅ TẠO MỘT RECORD PLACEHOLDER ĐỂ ĐÁNH DẤU SESSION ĐÃ CÓ ATTENDANCE
                // Hoặc có thể update ClassSession status, hoặc tạo AttendanceSession table riêng
                return String.format("Đã khởi tạo phiên điểm danh cho buổi học '%s' (Ngày: %s). " +
                                "Chưa có học sinh nào được điểm danh. Bạn có thể thêm học sinh sau.",
                        session.getTopic(), attendanceDate);
            }

            System.out.println("Processing " + attendanceRecords.size() + " attendance records...");

            // ✅ XỬ LÝ TỪNG ATTENDANCE RECORD
            for (int i = 0; i < attendanceRecords.size(); i++) {
                Map<String, Object> record = attendanceRecords.get(i);
                System.out.println("Processing record " + (i+1) + ": " + record);

                try {
                    String studentId = (String) record.get("studentId");
                    String statusStr = (String) record.get("status");
                    String note = (String) record.get("note");

                    System.out.println("  - studentId: " + studentId);
                    System.out.println("  - status: " + statusStr);
                    System.out.println("  - note: " + note);

                    // Validate required fields
                    if (studentId == null || studentId.trim().isEmpty()) {
                        System.out.println("  - ERROR: Student ID is null/empty");
                        errorCount++;
                        continue;
                    }
                    if (statusStr == null || statusStr.trim().isEmpty()) {
                        System.out.println("  - ERROR: Status is null/empty");
                        errorCount++;
                        continue;
                    }

                    // Kiểm tra điểm danh đã tồn tại chưa
                    boolean exists = attendanceRepository.existsBySessionIdAndStudentId(sessionId, studentId);
                    System.out.println("  - Attendance exists: " + exists);

                    if (exists) {
                        System.out.println("  - SKIPPED: Attendance already exists");
                        skippedCount++;
                        continue;
                    }

                    // Tìm student
                    AppUser student = appUserRepository.findById(studentId).orElse(null);
                    if (student == null) {
                        System.out.println("  - ERROR: Student not found with ID: " + studentId);
                        errorCount++;
                        continue;
                    }
                    System.out.println("  - Found student: " + student.getUserName());

                    // Validate AttendanceStatus
                    AttendanceStatus status;
                    try {
                        status = AttendanceStatus.valueOf(statusStr.trim());
                        System.out.println("  - Parsed status: " + status);
                    } catch (IllegalArgumentException e) {
                        System.out.println("  - ERROR: Invalid status: " + statusStr);
                        errorCount++;
                        continue;
                    }

                    // ✅ TẠO VÀ LƯU ATTENDANCE
                    String attendanceId = IdGenerator.generateUUID();
                    System.out.println("  - Generated attendance ID: " + attendanceId);

                    Attendance attendance = new Attendance();
                    attendance.setAttendanceId(attendanceId);
                    attendance.setSession(session);
                    attendance.setStudent(student);
                    attendance.setStatus(status);
                    attendance.setNote(note != null ? note : "");
                    attendance.setAttendanceDate(attendanceDate);

                    System.out.println("  - About to save attendance: " + attendance.getAttendanceId());

                    Attendance savedAttendance = attendanceRepository.save(attendance);
                    System.out.println("  - SAVED: " + savedAttendance.getAttendanceId());

                    createdCount++;

                } catch (Exception e) {
                    System.out.println("  - ERROR processing record: " + e.getMessage());
                    e.printStackTrace();
                    errorCount++;
                }
            }

            String result = String.format("Điểm danh hoàn tất: %d tạo mới, %d bỏ qua (đã tồn tại), %d lỗi",
                    createdCount, skippedCount, errorCount);

            System.out.println("=== FINAL RESULT: " + result + " ===");
            return result;

        } catch (Exception e) {
            System.out.println("=== EXCEPTION IN CREATE ATTENDANCE ===");
            e.printStackTrace();
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
