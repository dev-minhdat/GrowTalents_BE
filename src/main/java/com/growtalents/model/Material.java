package com.growtalents.model;

import com.growtalents.enums.MaterialType;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(
        name = "Material",
        uniqueConstraints = {
                // Đảm bảo mỗi course chỉ có 1 bản ghi cho mỗi loại thông tin
                @UniqueConstraint(name = "uk_material_course_type", columnNames = {"course_id", "type"})
        }
)
public class Material {

    @Id
    @Column(name = "material_id")
    private String materialId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false, length = 40)
    private MaterialType type; // COURSE_DESCRIPTION, LEARNING_OBJECTIVES, TEACHER_INFO

    @Column(name = "content", columnDefinition = "TEXT")
    private String content; // Nội dung chính của loại thông tin
}
