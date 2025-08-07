package com.growtalents.model;

import com.growtalents.enums.AssignedRole;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
@Entity
@Table (name = "TeacherCourse")
public class TeacherCourse {
    @Id
    @Column (name = "teachercourse_id")
    private int teacherCourseId;

    @Enumerated(EnumType.STRING)
    @Column (name = "assigned_role")
    private AssignedRole assignedRole;

    @Column (name = "hourly_rate")
    private int  hourlyRate;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name =  "teacher_id", nullable = false)
    private AppUser teacher;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id",nullable = false)
    private Course course;
}
