package com.growtalents.dto.response.Notification;

import com.growtalents.enums.NotificationType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NotificationResponseDTO {
    private String notificationId;
    private NotificationType type;
    private String title;
    private String content;
    private boolean read;
    private LocalDateTime createdAt;
    private String courseId;
}
