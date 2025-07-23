package com.hatal.ToDoListBackend.DTOs;

import com.hatal.ToDoListBackend.Entities.Task;
import com.hatal.ToDoListBackend.Enums.TaskStatus;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

import static com.hatal.ToDoListBackend.DTOs.UserInTaskDTO.mapUserToDTO;

@Data
public class TaskDTO {
    private int id;
    private String title;
    private String body;
    private UserInTaskDTO accomplishedBy;
    private List<UserInTaskDTO> assignedUsers;
    private TaskStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime accomplishedAt;

    public static List<TaskDTO> mapTasksToDTO(List<Task> tasks) {
        return tasks.stream().map((TaskDTO::mapTasksToDTO)).toList();
    }

    public static TaskDTO mapTasksToDTO(Task task) {
        TaskDTO taskDTO = new TaskDTO();

        taskDTO.setId(task.getId());
        taskDTO.setBody(task.getBody());
        taskDTO.setTitle(task.getTitle());
        taskDTO.setStatus(task.getStatus());
        taskDTO.setCreatedAt(task.getCreatedAt());
        taskDTO.setAccomplishedAt(task.getAccomplishedAt());

        taskDTO.setAccomplishedBy(mapUserToDTO(task.getAccomplishedBy()));
        taskDTO.setAssignedUsers(mapUserToDTO(task.getAssignedUsers()));

        return taskDTO;
    }
}
