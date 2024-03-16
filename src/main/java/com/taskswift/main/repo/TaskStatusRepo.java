package com.taskswift.main.repo;

import com.taskswift.main.entity.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskStatusRepo extends JpaRepository<TaskStatus, Long> {
}
