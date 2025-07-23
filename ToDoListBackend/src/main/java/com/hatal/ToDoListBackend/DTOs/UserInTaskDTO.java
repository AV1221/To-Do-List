package com.hatal.ToDoListBackend.DTOs;

import com.hatal.ToDoListBackend.Entities.User;
import lombok.Data;

import java.util.List;

@Data
public class UserInTaskDTO {
    private Long id;
    private String firstName;
    private String lastName;

    public static List<UserInTaskDTO> mapUserToDTO(List<User> users) {
        return users != null ? users.stream().map(UserInTaskDTO::mapUserToDTO).toList() : null;
    }

    public static UserInTaskDTO mapUserToDTO(User user) {
        if (user == null) return null;

        UserInTaskDTO userInTaskDTO = new UserInTaskDTO();

        userInTaskDTO.setId(user.getId());
        userInTaskDTO.setFirstName(user.getFirstName());
        userInTaskDTO.setLastName(user.getLastName());

        return userInTaskDTO;
    }
}
