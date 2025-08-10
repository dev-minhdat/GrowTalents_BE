    package com.growtalents.model;

    import com.growtalents.enums.RescheduleStatus;
    import jakarta.persistence.*;
    import lombok.*;

    import java.time.LocalDate;
    import java.time.LocalTime;

    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @ToString
    @Entity
    @Table(name = "ClassSession")
    public class ClassSession {
        @Id
        @Column(name = "session_id")
        private String sessionId;

        // Ngày kế hoạch ban đầu của buổi học
        // Dùng để so sánh khi có đề xuất dời lịch
        @Column(name = "original_date")
        private LocalDate originalDate;

        // ==== Các trường liên quan đến đề xuất đổi lịch ====

        // Trạng thái đề xuất dời lịch:
        // null => chưa từng đề xuất
        // PENDING => giáo viên đã gửi, đang chờ duyệt
        // APPROVED => admin duyệt, sẽ cập nhật lịch thực tế
        // REJECTED => admin từ chối, giữ nguyên lịch cũ
        @Enumerated(EnumType.STRING)
        @Column(name = "reschedule_status")
        private RescheduleStatus rescheduleStatus;

        // Lý do giáo viên đề xuất dời lịch (ghi chú)
        @Column(name = "detail")
        private String rescheduleReason;

        @Column(name = "proposed_date")
        private LocalDate proposedDate;

        @Column(name = "proposed_start_time")
        private LocalTime proposedStartTime;
        // ==== Kết thúc nhóm field đề xuất đổi lịch ====

        // Ngày thực tế diễn ra buổi học (có thể thay đổi nếu đề xuất được duyệt)
        @Column(name = "session_date")
        private LocalDate sessionDate;

        // Chủ đề buổi học
        @Column(name = "topic")
        private String topic;

        // Thời lượng buổi học (phút)
        @Column(name = "duration_in_minutes")
        private int durationInMinutes;

        // Thời gian bắt đầu thực tế (có thể thay đổi nếu đề xuất được duyệt)
        @Column(name = "start_time")
        private LocalTime startTime;

        // Thời gian kết thúc thực tế (có thể thay đổi nếu đề xuất được duyệt)
        @Column(name = "end_time")
        private LocalTime endTime;

        // Khóa học mà buổi học này thuộc về
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "course_id", nullable = false)
        private Course course;
    }
