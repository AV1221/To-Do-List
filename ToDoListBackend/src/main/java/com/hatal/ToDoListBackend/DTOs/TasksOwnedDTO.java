package com.hatal.ToDoListBackend.DTOs;

import com.hatal.ToDoListBackend.Entities.Task;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

import static com.hatal.ToDoListBackend.DTOs.UserInTaskDTO.mapUserToDTO;

@Setter
@Getter
public class TasksOwnedDTO extends TaskDTO {
    private UserInTaskDTO owner;

    public static List<TasksOwnedDTO> mapTasksToOwnedDTO(List<Task> tasks) {
        return tasks.stream().map(TasksOwnedDTO::mapTasksToOwnedDTO).toList();
    }


    public static TasksOwnedDTO mapTasksToOwnedDTO(Task task) {
        TasksOwnedDTO mappedTask = new TasksOwnedDTO();

        mappedTask.setId(task.getId());
        mappedTask.setBody(task.getBody());
        mappedTask.setTitle(task.getTitle());
        mappedTask.setStatus(task.getStatus());
        mappedTask.setCreatedAt(task.getCreatedAt());
        mappedTask.setAccomplishedAt(task.getAccomplishedAt());

        mappedTask.setAccomplishedBy(mapUserToDTO(task.getAccomplishedBy()));
        mappedTask.setAssignedUsers(mapUserToDTO(task.getAssignedUsers()));

        mappedTask.setOwner(UserInTaskDTO.mapUserToDTO(task.getOwner()));

        return mappedTask;
    }

}

