package com.hatal.ToDoListBackend.Controllers;

import com.hatal.ToDoListBackend.DTOs.TaskDTO;
import com.hatal.ToDoListBackend.DTOs.UserDTO;
import com.hatal.ToDoListBackend.DTOs.UserRegisterDTO;
import com.hatal.ToDoListBackend.Entities.User;
import com.hatal.ToDoListBackend.Enums.TaskType;
import com.hatal.ToDoListBackend.Services.UserService;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user) {
        try {
            return ResponseEntity.status(HttpStatus.CREATED).body(userService.createUser(user));

        } catch (EntityExistsException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<UserDTO> register(@RequestBody UserRegisterDTO user) {
        try {
            return ResponseEntity.ok(userService.register(user.getEmail(), user.getPassword()));
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        }
    }

    @GetMapping("/{id}/done")
    public ResponseEntity<List<TaskDTO>> getAllTasksCompleted(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(userService.getAllTasksByUserIdAndTaskType(id, TaskType.COMPLETED));
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping("/{id}/owned")
    public ResponseEntity<List<TaskDTO>> getAllTasksOwned(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(userService.getAllTasksByUserIdAndTaskType(id, TaskType.OWNED));
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping("/{id}/shared")
    public ResponseEntity<List<TaskDTO>> getAllTasksShared(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(userService.getAllTasksByUserIdAndTaskType(id, TaskType.SHARED));
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}

