package me.dio.domain.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "tb_task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "completed_at")
    private LocalDateTime completedAt;

    @Enumerated(EnumType.STRING)
    private Status status;

    // Getters and Setters
}

enum Status {
    PENDING,
    IN_PROGRESS,
    COMPLETED
}
