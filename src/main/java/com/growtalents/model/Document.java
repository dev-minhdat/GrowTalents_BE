package com.growtalents.model;


import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "Document")
public class Document {

    @Id
    @Column(name = "document_id")
    private String documentId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "lesson_id", nullable = false)
    private Lesson lesson;

    @Column(nullable = false)
    private String title;

    @Lob
    private String description;

    @Column(name = "file_url", length = 1024)
    private String fileUrl;

    @Column(name = "uploaded_at")
    private LocalDateTime uploadedAt;
}
