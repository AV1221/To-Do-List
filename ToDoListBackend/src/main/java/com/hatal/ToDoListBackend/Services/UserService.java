package com.hatal.ToDoListBackend.Services;

import com.hatal.ToDoListBackend.DTOs.TaskDTO;
import com.hatal.ToDoListBackend.DTOs.TasksOwnedDTO;
import com.hatal.ToDoListBackend.DTOs.UserDTO;
import com.hatal.ToDoListBackend.Entities.User;
import com.hatal.ToDoListBackend.Enums.TaskType;
import com.hatal.ToDoListBackend.Repositories.UserRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.h2.message.DbException;
import org.hibernate.exception.ConstraintViolationException;
import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    //create a new user
    public User createUser(User user) {
        try {
            String pass = user.getPassword();
            String hashPass = BCrypt.hashpw(pass, BCrypt.gensalt());

            user.setPassword(hashPass);

            return userRepository.save(new User(user));
        } catch (Exception e) {
            if (e instanceof DataIntegrityViolationException) {
                Throwable cause = ((DataIntegrityViolationException) e).getCause();

                if (cause instanceof ConstraintViolationException) {
                    if (((ConstraintViolationException) cause).getSQLException().getErrorCode() == 23505) {
                        throw new EntityExistsException("Email already exists: " + user.getEmail());
                    }
                }
            }

            throw e;
        }
    }

    public UserDTO register(String email, String password) {
        return userRepository.findByEmail(email).filter(user -> BCrypt.checkpw(password, user.getPassword()))
                .map(UserDTO::toUserDto)
                .orElseThrow(() -> new EntityNotFoundException("Could not find match to password / email a user"));
    }

    public List<TaskDTO> getAllTasksByUserIdAndTaskType(Long id, TaskType taskType) {
        User user = getUserById(id);
        List<TaskDTO> taskList = new ArrayList<>();

        switch (taskType) {
            case COMPLETED -> taskList.addAll(TasksOwnedDTO.mapTasksToOwnedDTO(user.getAccomplishedTasks()));
            case OWNED -> taskList.addAll(TaskDTO.mapTasksToDTO(user.getOwnedTasks()));
            case SHARED -> taskList.addAll(TasksOwnedDTO.mapTasksToOwnedDTO(user.getSharedTasks()));
        }

        return taskList;
    }

    private User getUserById(Long id) {
        return this.userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
    }
}

