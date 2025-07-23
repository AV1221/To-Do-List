package com.hatal.ToDoListBackend.Repositories;

import com.hatal.ToDoListBackend.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    @Query("SELECT su FROM users su " +
            "WHERE su.id NOT IN (SELECT u.id FROM users u " +
            "JOIN u.ownedTasks ot " +
            "JOIN u.sharedTasks st " +
            "WHERE (ot.id = :taskId) OR (st.id = :taskId))")
    List<User> findUsersToShareWith(@Param("taskId") int taskId);
}
