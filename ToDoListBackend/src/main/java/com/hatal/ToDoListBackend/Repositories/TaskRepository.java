package com.hatal.ToDoListBackend.Repositories;

import com.hatal.ToDoListBackend.Entities.Task;
import com.hatal.ToDoListBackend.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface TaskRepository extends JpaRepository<Task, Integer> {
}
