package com.taskswift.main.dao;

import com.taskswift.main.entity.Task;
import com.taskswift.main.entity.TaskTimeline;
import com.taskswift.main.repo.TaskTimelineRepo;
import com.taskswift.main.service.TaskService;
import com.taskswift.main.util.TenantUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class TaskTimelineDaoImpl implements TaskTimelineDao{

    private static final Logger logger = LoggerFactory.getLogger(TaskTimelineDaoImpl.class);

    @Autowired
    private TaskTimelineRepo taskTimelineRepo;

    @Autowired
    private TaskService taskService;

    @Override
    public void addActivity(TaskTimeline taskTimeline) {
        taskTimeline.setActivityId(TenantUtil.getNextUniqueId());
        taskTimelineRepo.save(taskTimeline);
        logger.info(">>> Saved \"{}\" activity of {} in timeline to DB", taskTimeline.getMessage(), taskTimeline.getTask().getTaskTitle());
    }

    @Override
    public List<TaskTimeline> getActivitiesForTask(Long taskId) {
        Task task = taskService.getTaskById(taskId);
        return taskTimelineRepo.findAllByTaskAndActivityIdBetween(task, TenantUtil.currentTenant.getStartRange(), TenantUtil.currentTenant.getEndRange());
    }
}
