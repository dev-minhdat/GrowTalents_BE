package com.growtalents.model;

import com.growtalents.enums.PerformanceLevel;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
@Entity
@Table(name = "Grade")
public class Grade {

    @Id
    @Column(name = "grade_id")
    private String gradeId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_id", nullable = false)
    private AppUser student;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "course_id", nullable = false)
//    private Course course;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "assignment_id")
    private Assignment assignment;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "class_session_id")
//    private ClassSession classSession;

    @Enumerated(EnumType.STRING)
    @Column(name = "performance_level")
    private PerformanceLevel performanceLevel;

    @Column(name = "score")
    private Float score;

    @Lob
    @Column(name = "comment")
    private String comment;
}