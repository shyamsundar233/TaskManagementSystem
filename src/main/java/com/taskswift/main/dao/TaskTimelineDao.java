package com.taskswift.main.dao;

import com.taskswift.main.entity.TaskTimeline;

import java.util.List;

public interface TaskTimelineDao {

    public void addActivity(TaskTimeline taskTimeline);

    public List<TaskTimeline> getActivitiesForTask(Long taskId);

}
