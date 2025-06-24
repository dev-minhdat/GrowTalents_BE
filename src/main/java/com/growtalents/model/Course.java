package com.growtalents.model;

import com.growtalents.enums.CourseType;
import jakarta.persistence.*;
import lombok.*;

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

    @Column (name = "tuition_fee")
    private Integer tuitionFee;

    @Column (name = "duration")
    private String duration;

    @Lob
    @Column (name = "description")
    private String description;

    @Enumerated
    @Column (name = "type" )
    private CourseType type;

    @Lob
    @Column (name = "syllabus")
    private String syllabus;
}
