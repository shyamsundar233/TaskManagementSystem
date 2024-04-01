package com.taskswift.main.repo;

import com.taskswift.main.entity.Task;
import com.taskswift.main.entity.TaskStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskStatusRepo extends JpaRepository<TaskStatus, Long> {

    List<TaskStatus> findAllByTaskAndTaskStatusIdBetween(Task task, Long startRange, Long endRange);

    void deleteAllByTaskAndTaskStatusIdBetween(Task task, Long startRange, Long endRange);

}
