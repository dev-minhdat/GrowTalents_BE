package com.growtalents.model;

import com.growtalents.enums.CourseStatus;
import com.growtalents.enums.CourseType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Builder
@Table (name = "Course")
public class Course {
    @Id
    @Column (name = "course_id")
    private String courseId;

    @Column (name = "name", nullable = false)
    private String name;

    @Column(name="image_url", length = 1000)
    private String imageUrl;

    @Column (name = "tuition_fee")
    private Integer tuitionFee;

    @Column (name = "duration")
    private Integer duration;

    @Lob
    @Column (name = "description")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column (name = "type" )
    private CourseType type;

    @Enumerated(EnumType.STRING)
    @Column (name = "status")
    private CourseStatus status;

    @Column(name = "created_at", updatable = false)
    private LocalDate createdAt;


    @Column(name = "last_modified")
    private LocalDate lastModified;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by")
    private AppUser createdBy;

    @PrePersist
    public void prePersist() {
        if (status == null) {
            status = CourseStatus.ACTIVE;
        }
    }
}
