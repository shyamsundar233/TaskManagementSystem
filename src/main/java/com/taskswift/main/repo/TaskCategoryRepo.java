package com.taskswift.main.repo;

import com.taskswift.main.entity.TaskCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskCategoryRepo extends JpaRepository<TaskCategory, Long> {
}
