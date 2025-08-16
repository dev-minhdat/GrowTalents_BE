package com.growtalents.service.Implement;

import com.growtalents.dto.response.Student.*;
import com.growtalents.enums.StudentCourseStatus;
import com.growtalents.enums.UserRole;
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
    private final SemesterRepository semesterRepository;
    private final CourseRepository courseRepository;

    @Override
    public List<StudentListResponseDTO> getAllStudents() {
        List<AppUser> students = appUserRepository.findAllByUserRole(UserRole.STUDENT);
        
        return students.stream()
                .map(student -> StudentListResponseDTO.builder()
                        .userId(student.getUserId())
                        .userName(student.getUserName())
                        .userEmail(student.getUserEmail())
                        .userPhone(student.getUserPhone())
                        .userParentName(student.getUserParentName())
                        .userParentPhone(student.getUserParentPhone())
                        .userStatus(student.getUserStatus() != null ? student.getUserStatus().name() : null)
                        .description(student.getDescription())
                        .build())
                .collect(Collectors.toList());
    }

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

    @Override
    public List<YearResponseDTO> getAllYears() {
        List<Integer> years = semesterRepository.findDistinctYears();
        
        return years.stream()
                .map(year -> {
                    List<Semester> semesters = semesterRepository.findByYearOrderBySemesterName(year);
                    return YearResponseDTO.builder()
                            .year(year)
                            .description("Năm học " + year)
                            .totalSemesters(semesters.size())
                            .build();
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<SemesterResponseDTO> getSemestersByYear(Integer year) {
        List<Semester> semesters = semesterRepository.findByYearOrderBySemesterName(year);
        
        return semesters.stream()
                .map(semester -> SemesterResponseDTO.builder()
                        .semesterId(semester.getSemesterId())
                        .semesterName(semester.getName()) // Sử dụng getName() thay vì getSemesterName()
                        .year(year) // Sử dụng parameter year
                        .status(semester.getStatus() != null ? semester.getStatus().name() : null)
                        .startDate(semester.getStartDate() != null ? semester.getStartDate().toString() : null)
                        .endDate(semester.getEndDate() != null ? semester.getEndDate().toString() : null)
                        .build())
                .collect(Collectors.toList());
    }

    @Override
    public List<ClassResponseDTO> getClassesBySemester(String semesterId) {
        // Validate semester exists
        semesterRepository.findById(semesterId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy kỳ học với ID: " + semesterId));

        // Get courses in this semester via SemesterCourse relationship
        List<Course> courses = courseRepository.findBySemesterId(semesterId);
        
        return courses.stream()
                .map(course -> {
                    // Get teacher name - assuming first teacher found
                    String teacherName = courseRepository.findTeacherNameByCourseId(course.getCourseId());
                    
                    // Get total students enrolled
                    Integer totalStudents = courseRepository.countStudentsByCourseId(course.getCourseId());
                    
                    return ClassResponseDTO.builder()
                            .courseId(course.getCourseId())
                            .courseName(course.getName())
                            .courseType(course.getType() != null ? course.getType().name() : null)
                            .courseStatus(course.getStatus() != null ? course.getStatus().name() : null)
                            .duration(course.getDuration() != null ? course.getDuration().toString() : null)
                            .tuitionFee(course.getTuitionFee())
                            .description(course.getDescription())
                            .teacherName(teacherName)
                            .totalStudents(totalStudents)
                            .build();
                })
                .collect(Collectors.toList());
    }
}
