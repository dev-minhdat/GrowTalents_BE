package com.growtalents.model;

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


    @Column (name = "type" )
    private String type;

    @Lob
    @Column (name = "syllabus")
    private String syllabus;
}
