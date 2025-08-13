package com.growtalents.service.Implement;

import com.growtalents.dto.response.Notification.NotificationResponseDTO;
import com.growtalents.enums.NotificationType;
import com.growtalents.enums.StudentCourseStatus;
import com.growtalents.mapper.NotificationMapper;
import com.growtalents.model.Notification;
import com.growtalents.repository.CourseRepository;
import com.growtalents.repository.NotificationRepository;
import com.growtalents.repository.StudentCourseRepository;
import com.growtalents.service.Interfaces.NotificationService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepo;
    private final StudentCourseRepository studentCourseRepo;
    private final CourseRepository courseRepo;

    @Override
    @Transactional
    public void createScheduleChangeForCourseAndStudents(String courseId, String changeSummary) {
        var course = courseRepo.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Course not found: " + courseId));

        var enrolled = studentCourseRepo.findAllByCourse_CourseIdAndStatus(courseId, StudentCourseStatus.ENROLLED);

        for (var sc : enrolled) {
            var n = Notification.builder()
                    .notificationId(UUID.randomUUID().toString())
                    .receiver(sc.getStudent())
                    .course(course)
                    .type(NotificationType.SCHEDULE_CHANGED)
                    .title("Lịch học đã thay đổi")
                    .content(changeSummary)
                    .build();
            notificationRepo.save(n);
        }
    }

    @Override
    public List<NotificationResponseDTO> getStudentNotifications(String studentId) {
        return notificationRepo.findAllByStudent(studentId).stream()
                .map(NotificationMapper::toDTO).toList();
    }

    @Override
    public List<NotificationResponseDTO> getStudentNotificationsByCourse(String studentId, String courseId) {
        return notificationRepo.findAllByStudentAndCourse(studentId, courseId).stream()
                .map(NotificationMapper::toDTO).toList();
    }
}
