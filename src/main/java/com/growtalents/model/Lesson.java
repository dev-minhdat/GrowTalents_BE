package com.growtalents.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Lesson")
public class Lesson {

    @Id
    @Column(name = "lesson_id")
    private String lessonId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chapter_id", nullable = false)
    private Chapter chapter;

    @Column(nullable = false)
    private String title;

    @Lob
    private String content;

    @Column(name = "order_index", nullable = false)
    private int orderIndex;
}
