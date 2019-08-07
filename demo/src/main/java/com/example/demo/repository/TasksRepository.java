package com.example.demo.repository;

import com.example.demo.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TasksRepository extends JpaRepository<Task, Long> {

    List<Task> findByNameContaining(@Param("name") String searchText);

    Optional<Task> findByNameEquals(@Param("name") String searchText);
}
