package com.growtalents.model;

import com.growtalents.enums.AssignmentType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Entity
@Table (name = "Assignment")
public class Assignment {
    @Id
    @Column(name = "assignment_id")
    private String assignmentId;

    @Column(name = "title", nullable = false)
    private String title;

    @Lob
    @Column(name="description",nullable = false)
    private String description;

    @Column (name="upload_file_url" , length = 1024)
    private String uploadFileUrl;

    @Column(name="deadline")
    private LocalDate deadline;

    @Column(name = "assignment_type")
    private AssignmentType assignmentType;

    @ManyToOne (fetch = FetchType.LAZY)
    @JoinColumn (name = "course_id",nullable = false)
    private Course course;

}
