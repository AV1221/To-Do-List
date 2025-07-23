package com.hatal.ToDoListBackend.DTOs;

import lombok.Data;

@Data
public class TaskCreateDTO {
    private String title;
    private String body;
    private Long ownerId;
}
