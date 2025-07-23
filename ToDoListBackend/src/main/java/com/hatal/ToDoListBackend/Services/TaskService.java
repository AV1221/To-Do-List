package com.hatal.ToDoListBackend.Services;

import com.hatal.ToDoListBackend.DTOs.TaskCreateDTO;
import com.hatal.ToDoListBackend.DTOs.TasksOwnedDTO;
import com.hatal.ToDoListBackend.DTOs.UserDTO;
import com.hatal.ToDoListBackend.Entities.Task;
import com.hatal.ToDoListBackend.Entities.User;
import com.hatal.ToDoListBackend.Enums.TaskStatus;
import com.hatal.ToDoListBackend.Repositories.TaskRepository;
import com.hatal.ToDoListBackend.Repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.management.relation.RelationNotFoundException;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    public TasksOwnedDTO getTaskDTOById(int id) {
        return TasksOwnedDTO.mapTasksToOwnedDTO(getTaskById(id));
    }

    public Task createTask(TaskCreateDTO task) {
        Task newTask = new Task();

        newTask.setTitle(task.getTitle());
        newTask.setBody(task.getBody());
        newTask.setOwner(this.getUserById(task.getOwnerId()));
        newTask.setStatus(TaskStatus.TODO);
        newTask.setCreatedAt(LocalDateTime.now());

        return taskRepository.save(newTask);
    }

    public void deleteTask(int id) {
        Task task = getTaskById(id);

        for (User user : task.getAssignedUsers()) {
            user.getSharedTasks().remove(task);
            userRepository.save(user);
        }

        taskRepository.deleteById(id);
    }

    public void assignTaskToUser(int taskId, Long userId) {
        User user = getUserById(userId);
        Task task = getTaskById(taskId);

        user.getSharedTasks().add(task);

        this.userRepository.save(user);
    }

    public void complete(int taskId, Long userId) throws RelationNotFoundException {
        Task task = getTaskById(taskId);

        if (task.getStatus().equals(TaskStatus.DONE)) {
            throw new IllegalStateException("Task is already completed.");
        }

        boolean isOwner = task.getOwner().getId().equals(userId);
        boolean isAssigned = task.getAssignedUsers().stream()
                .anyMatch(user -> user.getId().equals(userId));

        User user = getUserById(userId);

        if (isOwner || isAssigned ) {

            task.setAccomplishedBy(user);
            task.setAccomplishedAt(LocalDateTime.now());
            task.setStatus(TaskStatus.DONE);

            user.getAccomplishedTasks().add(task);

            this.taskRepository.save(task);
        } else{
            throw new RelationNotFoundException("User with id: " + userId +
                    " is not associated to task id: " + taskId);
        }
    }

    private User getUserById(Long id) {
        return this.userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + id));
    }

    private Task getTaskById(int id) {
        return this.taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task not found with id: " + id));
    }

    //get all users that a task could be shared with
    public List<UserDTO> getAllUsersCanBeSharedToTaskById(int taskId) {

        if (this.taskRepository.existsById(taskId))
            return userRepository.findUsersToShareWith(taskId).stream().map(UserDTO::toUserDto).toList();
        else
            throw new EntityNotFoundException("Task not found with id: " + taskId);
    }
}

