package com.taskswift.main.service;

import com.taskswift.main.dao.TaskTimelineDao;
import com.taskswift.main.entity.TaskTimeline;

import java.util.List;

public interface TaskTimelineService {

    public void addActivity(TaskTimeline taskTimeline);

    public List<TaskTimeline> getActivitiesForTask(Long taskId);

}
