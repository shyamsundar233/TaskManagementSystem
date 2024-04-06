//$Id$
package com.taskswift.main.repo;

import com.taskswift.main.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.taskswift.main.entity.Task;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TaskRepo extends JpaRepository<Task, Long> {

    Page<Task> findAllByTaskIdIsBetween(Long startRange, Long endRange, Pageable pageable);

    List<Task> findAllByDueDateAndTaskIdIsBetween(LocalDate date, Long startRange, Long endRange);

    List<Task> findAllByDueDateBetweenAndTaskIdIsBetween(LocalDate fromDate, LocalDate toDate, Long startRange, Long endRange);

    Optional<Task> findByTaskIdAndTaskIdIsBetween(Long taskId, Long startRange, Long endRange);

    List<Task> findAllByUserAndTaskIdIsBetween(User user, Long startRange, Long endRange);

    List<Task> findByTaskStatusAndTaskIdIsBetween(String taskStatus, Long startRange, Long endRange);

    List<Task> findByTaskPriorityAndTaskIdIsBetween(String taskPriority, Long startRange, Long endRange);

}
