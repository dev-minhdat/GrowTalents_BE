package com.growtalents.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Syllabus")
public class Syllabus {

    @Id
    @Column(name = "syllabus_id")
    private String syllabusId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false, unique = true)
    private Course course;

    @Column(nullable = false)
    private String title;

    @Lob
    private String description;
}
