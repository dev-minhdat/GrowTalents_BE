package com.growtalents.service.Interfaces;

import com.growtalents.dto.response.Student.*;

import java.time.LocalDate;
import java.util.List;

public interface StudentService {
    
    /**
     * Lấy danh sách buổi học trong tuần theo định dạng ngày đầu và cuối
     * @param studentId ID của học sinh
     * @param startDate Ngày bắt đầu (dd/MM/yyyy)
     * @param endDate Ngày kết thúc (dd/MM/yyyy)
     * @return Danh sách buổi học
     */
    List<StudentClassSessionResponseDTO> getWeeklyClassSessions(String studentId, LocalDate startDate, LocalDate endDate);
    
    /**
     * Lấy danh sách khóa học mà học sinh đã đăng ký
     * @param studentId ID của học sinh
     * @return Danh sách khóa học đã đăng ký
     */
    List<StudentCourseResponseDTO> getEnrolledCourses(String studentId);
    
    /**
     * Lấy danh sách bài tập hiện có trong khóa học theo thứ tự mới nhất
     * @param studentId ID của học sinh
     * @return Danh sách bài tập theo thứ tự mới nhất
     */
    List<StudentAssignmentResponseDTO> getAssignmentsByStudent(String studentId);
    
    /**
     * Lấy thống kê của học sinh
     * @param studentId ID của học sinh
     * @return Thống kê bao gồm điểm trung bình, bài kiểm tra đã làm/chưa làm
     */
    StudentStatisticsResponseDTO getStudentStatistics(String studentId);
    
    /**
     * Lấy comment của giáo viên cho các phần bài tập mà student đã làm
     * @param studentId ID của học sinh
     * @return Danh sách comment của giáo viên
     */
    List<StudentGradeCommentResponseDTO> getTeacherComments(String studentId);
}
