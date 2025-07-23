package com.hatal.ToDoListBackend.DTOs;

import com.hatal.ToDoListBackend.Entities.User;
import lombok.Data;

import java.util.List;


@Data
public class UserDTO {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private List<TaskDTO> ownedTasks;
    private List<TasksOwnedDTO> sharedTasks;
    private List<TasksOwnedDTO> accomplishedTasks;

    public static UserDTO toUserDto(User user) {
        UserDTO mappedUser = new UserDTO();

        mappedUser.setId(user.getId());
        mappedUser.setFirstName(user.getFirstName());
        mappedUser.setLastName(user.getLastName());
        mappedUser.setEmail(user.getEmail());

        mappedUser.setOwnedTasks(TaskDTO.mapTasksToDTO(user.getOwnedTasks()));
        System.out.println("after set owned");

        mappedUser.setAccomplishedTasks(TasksOwnedDTO.mapTasksToOwnedDTO(user.getAccomplishedTasks()));
        System.out.println("after set completed");

        mappedUser.setSharedTasks(TasksOwnedDTO.mapTasksToOwnedDTO(user.getSharedTasks()));
        System.out.println("after set shared");

        return mappedUser;
    }
}

