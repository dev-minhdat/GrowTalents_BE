package com.growtalents.service.Interfaces;

import com.growtalents.dto.response.Notification.NotificationResponseDTO;

import java.util.List;

public interface NotificationService {
    void createScheduleChangeForCourseAndStudents(String courseId, String changeSummary);
    List<NotificationResponseDTO> getStudentNotifications(String studentId);
    List<NotificationResponseDTO> getStudentNotificationsByCourse(String studentId, String courseId);
}
