package com.hatal.ToDoListBackend.Entities;

import com.hatal.ToDoListBackend.Enums.TaskStatus;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity(name = "users")
@Data
@NoArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;

    @Column(unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "owner")
    private List<Task> ownedTasks;

    @ManyToMany
    @JoinTable(
            name = "users_tasks",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "task_id")
    )
    private List<Task> sharedTasks;

    @Transient
    private List<Task> accomplishedTasks;

    // Constructors
    public User(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public User(User user) {
        this(user.firstName, user.lastName, user.email, user.password);
    }

    @PostLoad
    private void populateAccomplishedSharedTasks() {
        accomplishedTasks = new ArrayList<>();
        addCompletedTasks(ownedTasks);
        addCompletedTasks(sharedTasks);
    }

    private void addCompletedTasks(List<Task> tasks) {
        if (tasks != null) {
            accomplishedTasks.addAll(tasks.stream()
                    .filter(task -> TaskStatus.DONE.equals(task.getStatus()))
                    .toList());
        } else {
            accomplishedTasks = List.of();
        }
    }
}

