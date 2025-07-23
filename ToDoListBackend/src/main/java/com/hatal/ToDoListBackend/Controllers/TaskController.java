package com.hatal.ToDoListBackend.Controllers;

import com.hatal.ToDoListBackend.DTOs.TaskCreateDTO;
import com.hatal.ToDoListBackend.DTOs.TasksOwnedDTO;
import com.hatal.ToDoListBackend.DTOs.UserDTO;
import com.hatal.ToDoListBackend.Entities.Task;
import com.hatal.ToDoListBackend.Entities.User;
import com.hatal.ToDoListBackend.Services.TaskService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.management.relation.RelationNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody TaskCreateDTO task) {
        Task created = taskService.createTask(task);

        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TasksOwnedDTO> getTaskById(@PathVariable int id) {
        try {
            return ResponseEntity.ok(taskService.getTaskDTOById(id));
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTask(@PathVariable int id) {
        try {
            taskService.deleteTask(id);

            return ResponseEntity.ok("Task id: " + id + " was deleted successfully");
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PatchMapping("/{id}/assignees")
    public ResponseEntity<String> assign(@PathVariable int id, @RequestBody User user) {
        try {
            taskService.assignTaskToUser(id, user.getId());

            return ResponseEntity.ok("Task id: " + id + " was assigned successfully to User: " + user.getId());
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @PatchMapping("/{id}/done")
    public ResponseEntity<String> complete(@PathVariable int id, @RequestBody User user) {
        try {
            taskService.complete(id, user.getId());

            return ResponseEntity.ok("Task id: " + id + " was completed successfully by User: " + user.getId());
        } catch (EntityNotFoundException | RelationNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage());
        } catch (IllegalStateException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, e.getMessage());
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @GetMapping("/{id}/unassigned-users")
    public List<UserDTO> getAllUsersNotAssignedToTask(@PathVariable int id) {
        return taskService.getAllUsersCanBeSharedToTaskById(id);
    }
}

