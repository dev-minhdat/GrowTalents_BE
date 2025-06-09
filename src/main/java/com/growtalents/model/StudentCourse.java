package com.growtalents.model;

import com.growtalents.enums.StudentCourseStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table (name = "StudentCourse")
public class StudentCourse {
    @Id
    @Column (name = "studentcourse_id")
    private String studentCourseId;

    @Column (name = "registration_date")
    private LocalDate registrationDate;

    @Column (name = "dropped_out_date")
    private LocalDate droppedOutDate;

    @Column (name = "status")
    private StudentCourseStatus status;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "student_id", nullable = false)
    private AppUser student;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "course_id", nullable = false)
    private Course course;

}
