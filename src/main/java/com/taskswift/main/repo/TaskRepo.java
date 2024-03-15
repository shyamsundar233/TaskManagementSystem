//$Id$
package com.taskswift.main.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.taskswift.main.entity.Task;

import java.time.LocalDate;
import java.util.List;

public interface TaskRepo extends JpaRepository<Task, Long> {

    List<Task> findAllByTaskIdIsBetween(Long startRange, Long endRange);

    List<Task> findAllByDueDate(LocalDate date);

    List<Task> findAllByDueDateBetween(LocalDate fromDate, LocalDate toDate);

}
