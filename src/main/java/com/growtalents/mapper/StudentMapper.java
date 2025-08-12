package com.growtalents.mapper;

import com.growtalents.dto.response.Student.*;
import com.growtalents.model.*;
import org.springframework.stereotype.Component;

@Component
public class StudentMapper {

    public static StudentClassSessionResponseDTO toClassSessionResponseDTO(ClassSession session) {
        return StudentClassSessionResponseDTO.builder()
                .sessionId(session.getSessionId())
                .sessionDate(session.getSessionDate())
                .topic(session.getTopic())
                .durationInMinutes(session.getDurationInMinutes())
                .startTime(session.getStartTime())
                .endTime(session.getEndTime())
                .courseId(session.getCourse().getCourseId())
                .courseName(session.getCourse().getName())
                .courseType(session.getCourse().getType() != null ? session.getCourse().getType().getDisplayName() : null)
                .build();
    }

    public static StudentCourseResponseDTO toCourseResponseDTO(StudentCourse studentCourse) {
        Course course = studentCourse.getCourse();
        return StudentCourseResponseDTO.builder()
                .courseId(course.getCourseId())
                .courseName(course.getName())
                .description(course.getDescription())
                .courseType(course.getType())
                .courseStatus(course.getStatus())
                .tuitionFee(course.getTuitionFee())
                .duration(course.getDuration())
                .enrollmentStatus(studentCourse.getStatus())
                .registrationDate(studentCourse.getRegistrationDate())
                .droppedOutDate(studentCourse.getDroppedOutDate())
                .build();
    }

    public static StudentAssignmentResponseDTO toAssignmentResponseDTO(Assignment assignment, 
                                                                       StudentSubmission submission, 
                                                                       Grade grade) {
        return StudentAssignmentResponseDTO.builder()
                .assignmentId(assignment.getAssignmentId())
                .title(assignment.getTitle())
                .description(assignment.getDescription())
                .assignmentType(assignment.getAssignmentType())
                .uploadFileUrl(assignment.getUploadFileUrl())
                .courseId(assignment.getLesson().getChapter().getSyllabus().getCourse().getCourseId())
                .courseName(assignment.getLesson().getChapter().getSyllabus().getCourse().getName())
                .createdAt(null) // Will be updated when createdAt field is added
                .hasSubmitted(submission != null)
                .submittedAt(submission != null ? submission.getSubmittedAt() : null)
                .submissionFileUrl(submission != null ? submission.getFileUrl() : null)
                .score(grade != null ? grade.getScore() : null)
                .teacherComment(grade != null ? grade.getComment() : null)
                .build();
    }

    public static StudentGradeCommentResponseDTO toGradeCommentResponseDTO(Grade grade) {
        Assignment assignment = grade.getAssignment();
        Course course = grade.getAssignment().getLesson().getChapter().getSyllabus().getCourse();
        
        return StudentGradeCommentResponseDTO.builder()
                .assignmentId(assignment.getAssignmentId())
                .assignmentTitle(assignment.getTitle())
                .assignmentType(assignment.getAssignmentType())
                .courseId(course.getCourseId())
                .courseName(course.getName())
                .score(grade.getScore())
                .teacherComment(grade.getComment())
                .submittedAt(null) // Will need to get from submission
                .gradedAt(null) // Will be updated when gradedAt field is added
                .teacherName("Teacher") // Will need to get teacher info
                .build();
    }

    public static StudentStatisticsResponseDTO toStatisticsResponseDTO(
            Float averageScore,
            int completedAssignments,
            int totalAssignments,
            int totalCoursesEnrolled,
            int attendedSessions,
            int totalSessions) {
        
        int pendingAssignments = totalAssignments - completedAssignments;
        Float attendanceRate = totalSessions > 0 ? (float) attendedSessions / totalSessions * 100 : 0.0f;
        
        return StudentStatisticsResponseDTO.builder()
                .averageScore(averageScore != null ? averageScore : 0.0f)
                .completedAssignments(completedAssignments)
                .pendingAssignments(pendingAssignments)
                .totalAssignments(totalAssignments)
                .totalCoursesEnrolled(totalCoursesEnrolled)
                .totalClassSessionsAttended(attendedSessions)
                .attendanceRate(attendanceRate)
                .build();
    }
}
