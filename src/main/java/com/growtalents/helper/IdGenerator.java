package com.growtalents.helper;

import java.text.DecimalFormat;
import java.time.Year;
import java.util.UUID;

/**
 * Helper class để tạo ID dạng String cho tất cả các bảng trong hệ thống.
 * ID có định dạng rõ ràng để dễ tra cứu và phân biệt giữa các loại.
 */
public class IdGenerator {
    private static final DecimalFormat numberFormat = new DecimalFormat("000");

    private static String formatIndex(String prefix, int index) {
        return prefix + "_" + numberFormat.format(index);
    }

    // === USER RELATED ===
    public static String generateUserId(int index) {
        return formatIndex("USR", index); // USR_001
    }

    // === SEMESTER RELATED ===
    public static String generateSemesterId(int index) {
        return formatIndex("SEM", index); // SEM_001
    }

    // SC_2024_SPRING_MATH101
    public static String generateSemesterCourseId(String semesterId, String courseCode) {
        String sem = semesterId.replace("SEM_", "");
        return "SC_" + sem + "_" + courseCode.toUpperCase();
    }

    // === COURSE RELATED ===
    public static String generateCourseId(String subjectCode, int index) {
        return subjectCode.toUpperCase() + "_" + numberFormat.format(index); // MATH_002
    }

    public static String generateSyllabusId(String subjectCode, int index) {
        return "SYL_" + Year.now().getValue() + "_" + subjectCode.toUpperCase() + "_" + numberFormat.format(index); // SYL_2025_ENG_001
    }

    // === CONTENT STRUCTURE ===
    public static String generateChapterId(int index) {
        return formatIndex("CH", index); // CH_001
    }

    public static String generateLessonId(int index) {
        return formatIndex("LES", index); // LES_001
    }

    public static String generateDocumentId(int index) {
        return formatIndex("DOC", index); // DOC_001
    }

    // === ASSIGNMENT & SUBMISSION ===
    public static String generateAssignmentId(int index) {
        return formatIndex("ASM", index); // ASM_001
    }

    public static String generateSubmissionId(int index) {
        return formatIndex("SUB", index); // SUB_001
    }

    // === ATTENDANCE ===
    public static String generateAttendanceId(int index) {
        return formatIndex("ATT", index); // ATT_001
    }

    // === GRADING ===
    public static String generateGradeId(int index) {
        return formatIndex("GRD", index); // GRD_001
    }

    // === TEACHING LOG ===
    public static String generateTeachingLogId(int index) {
        return formatIndex("LOG", index); // LOG_001
    }

    // === STUDENT COURSE RELATION ===
    public static String generateStudentCourseId(String studentId, String courseId) {
        return "SCU_" + studentId + "_" + courseId; // SCU_USR_001_MATH_001
    }

    // === GENERIC ===
    public static String generateUUID() {
        return UUID.randomUUID().toString(); // uuid dạng 8a2f7b62-xxxx-xxxx-xxxx
    }

    // === MONTHLY SALARY ===
    public static String generateMonthlySalaryId(String teacherId, String month) {
        return "SAL_" + teacherId + "_" + month; // SAL_USR_001_2025-06
    }

    // === SESSION-LESSON RELATION ===
    public static String generateSessionLessonId(String sessionId, String lessonId) {
        return "SLES_" + sessionId + "_" + lessonId; // SLES_SES_001_LES_002
    }

    // === USER-COURSE ROLE ===
    public static String generateUserCourseId(String userId, String courseId) {
        return "UC_" + userId + "_" + courseId; // UC_USR_001_MATH_001
    }
}
