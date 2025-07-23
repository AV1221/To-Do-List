package com.hatal.ToDoListBackend.Entities;

import com.hatal.ToDoListBackend.Enums.TaskStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;
import java.util.List;

@Entity(name = "tasks")
@Data
@NoArgsConstructor
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;

    private String body;

    @ManyToOne
    @JoinColumn(nullable = true)
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private User owner;

    @ManyToOne
    @JoinColumn
    @OnDelete(action = OnDeleteAction.SET_NULL)
    private User accomplishedBy;

    @ManyToMany(mappedBy = "sharedTasks")
    private List<User> assignedUsers;

    @Enumerated(EnumType.STRING)
    private TaskStatus status;

    private LocalDateTime createdAt;

    private LocalDateTime accomplishedAt;

    // Constructors

    public Task(String title, String body, User owner, User accomplishedBy, String status, LocalDateTime createdAt,
                LocalDateTime accomplishedAt) {
        this.title = title;
        this.body = body;
        this.owner = owner;
        this.accomplishedBy = accomplishedBy;
        this.status = TaskStatus.valueOf(status);
        this.createdAt = createdAt;
        this.accomplishedAt = accomplishedAt;
    }
}
