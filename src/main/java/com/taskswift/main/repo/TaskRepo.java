//$Id$
package com.taskswift.main.repo;

import com.taskswift.main.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import com.taskswift.main.entity.Task;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface TaskRepo extends JpaRepository<Task, Long> {

    List<Task> findAllByTaskIdIsBetween(Long startRange, Long endRange);

    List<Task> findAllByDueDateAndTaskIdIsBetween(LocalDate date, Long startRange, Long endRange);

    List<Task> findAllByDueDateBetweenAndTaskIdIsBetween(LocalDate fromDate, LocalDate toDate, Long startRange, Long endRange);

    Optional<Task> findByTaskIdAndTaskIdIsBetween(Long taskId, Long startRange, Long endRange);

    List<Task> findAllByUserAndTaskIdIsBetween(User user, Long startRange, Long endRange);

}
