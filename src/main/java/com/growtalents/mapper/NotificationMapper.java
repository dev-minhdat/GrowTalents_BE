package com.growtalents.mapper;

import com.growtalents.dto.response.Notification.NotificationResponseDTO;
import com.growtalents.model.Notification;
import org.springframework.stereotype.Component;

@Component
public class NotificationMapper {
    public static NotificationResponseDTO toDTO(Notification n) {
        return NotificationResponseDTO.builder()
                .notificationId(n.getNotificationId())
                .type(n.getType())
                .title(n.getTitle())
                .content(n.getContent())
                .read(n.isRead())
                .createdAt(n.getCreatedAt())
                .courseId(n.getCourse().getCourseId())
                .build();
    }
}
