package com.growtalents.model;

import com.growtalents.enums.SemesterStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table (name = "Semester")
public class Semester {
    @Id
    @Column (name = "semester_id")
    private String semesterId;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "start_date")
    private LocalDate startDate;

    @Column(name = "end_date")
    private LocalDate endDate;

    @Enumerated
    @Column(name = "status")
    private SemesterStatus status;

}
