package com.growtalents.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "id_sequence")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class IdSequence {
    @Id
    @Column(name = "entity_name")
    private String entityName; // VD: "AppUser", "Course"

    @Column(name = "current_index")
    private Integer currentIndex;
}

