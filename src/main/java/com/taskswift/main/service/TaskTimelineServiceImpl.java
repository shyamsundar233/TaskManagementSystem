package com.taskswift.main.service;

import com.taskswift.main.dao.TaskTimelineDao;
import com.taskswift.main.entity.TaskTimeline;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskTimelineServiceImpl implements TaskTimelineService{

    @Autowired
    private TaskTimelineDao taskTimelineDao;

    @Override
    public void addActivity(TaskTimeline taskTimeline) {
        taskTimelineDao.addActivity(taskTimeline);
    }

    @Override
    public List<TaskTimeline> getActivitiesForTask(Long taskId) {
        return taskTimelineDao.getActivitiesForTask(taskId);
    }
}
