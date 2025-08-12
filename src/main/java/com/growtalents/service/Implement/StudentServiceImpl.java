package com.growtalents.service.Implement;

import com.growtalents.dto.response.Student.*;
import com.growtalents.enums.StudentCourseStatus;
import com.growtalents.mapper.StudentMapper;
import com.growtalents.model.*;
import com.growtalents.repository.*;
import com.growtalents.service.Interfaces.StudentService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final ClassSessionRepository classSessionRepository;
    private final StudentCourseRepository studentCourseRepository;
    private final AssignmentRepository assignmentRepository;
    private final StudentSubmissionRepository studentSubmissionRepository;
    private final GradeRepository gradeRepository;
    private final AttendanceRepository attendanceRepository;
    private final AppUserRepository appUserRepository;

    @Override
    public List<StudentClassSessionResponseDTO> getWeeklyClassSessions(String studentId, LocalDate startDate, LocalDate endDate) {
        // Validate student exists
        appUserRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy học sinh với ID: " + studentId));

        List<ClassSession> sessions = classSessionRepository
                .findStudentClassSessionsBetweenDates(studentId, startDate, endDate);

        return sessions.stream()
                .map(StudentMapper::toClassSessionResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<StudentCourseResponseDTO> getEnrolledCourses(String studentId) {
        // Validate student exists
        appUserRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy học sinh với ID: " + studentId));

        List<StudentCourse> enrolledCourses = studentCourseRepository
                .findByStudentIdAndStatus(studentId, StudentCourseStatus.ENROLLED);

        return enrolledCourses.stream()
                .map(StudentMapper::toCourseResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    public List<StudentAssignmentResponseDTO> getAssignmentsByStudent(String studentId) {
        // Validate student exists
        appUserRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy học sinh với ID: " + studentId));

        List<Assignment> assignments = assignmentRepository.findStudentAssignmentsOrderByNewest(studentId);

        // Get submissions for these assignments
        Map<String, StudentSubmission> submissionMap = studentSubmissionRepository
                .findByStudentIdOrderBySubmittedAtDesc(studentId)
                .stream()
                .collect(Collectors.toMap(
                        submission -> submission.getAssignment().getAssignmentId(),
                        submission -> submission,
                        (existing, replacement) -> existing
                ));

        // Get grades for these assignments
        Map<String, Grade> gradeMap = assignments.stream()
                .flatMap(assignment -> gradeRepository
                        .findByStudentIdAndAssignmentId(studentId, assignment.getAssignmentId())
                        .stream())
                .collect(Collectors.toMap(
                        grade -> grade.getAssignment().getAssignmentId(),
                        grade -> grade,
                        (existing, replacement) -> existing
                ));

        return assignments.stream()
                .map(assignment -> {
                    StudentSubmission submission = submissionMap.get(assignment.getAssignmentId());
                    Grade grade = gradeMap.get(assignment.getAssignmentId());
                    return StudentMapper.toAssignmentResponseDTO(assignment, submission, grade);
                })
                .collect(Collectors.toList());
    }

    @Override
    public StudentStatisticsResponseDTO getStudentStatistics(String studentId) {
        // Validate student exists
        appUserRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy học sinh với ID: " + studentId));

        // Calculate average score
        Float averageScore = gradeRepository.calculateAverageScoreByStudentId(studentId);

        // Count completed assignments
        int completedAssignments = (int)studentSubmissionRepository.countCompletedAssignmentsByStudentId(studentId);

        // Count total assignments
        int totalAssignments = assignmentRepository.countTotalAssignmentsByStudentId(studentId);

        // Count enrolled courses
        int totalCoursesEnrolled = studentCourseRepository.countEnrolledCoursesByStudentId(studentId);

        // Count attendance
        int attendedSessions = attendanceRepository.countPresentAttendanceByStudentId(studentId);
        int totalSessions = classSessionRepository.countTotalSessionsByStudentId(studentId);

        return StudentMapper.toStatisticsResponseDTO(
                averageScore,
                completedAssignments,
                totalAssignments,
                totalCoursesEnrolled,
                attendedSessions,
                totalSessions
        );
    }

    @Override
    public List<StudentGradeCommentResponseDTO> getTeacherComments(String studentId) {
        // Validate student exists
        appUserRepository.findById(studentId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy học sinh với ID: " + studentId));

        List<Grade> gradesWithComments = gradeRepository.findStudentGradeCommentsOrderByNewest(studentId);

        return gradesWithComments.stream()
                .map(StudentMapper::toGradeCommentResponseDTO)
                .collect(Collectors.toList());
    }
}
