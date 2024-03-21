package com.taskswift.main.repo;

import com.taskswift.main.entity.Task;
import com.taskswift.main.entity.TaskTimeline;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TaskTimelineRepo extends JpaRepository<TaskTimeline, Long> {

    public List<TaskTimeline> findAllByTaskAndActivityIdBetween(Task task, Long startRange, Long endRange);

}
