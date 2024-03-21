package com.taskswift.main.util;

import com.taskswift.main.entity.TaskTimeline;
import com.taskswift.main.service.TaskService;
import com.taskswift.main.service.TaskTimelineService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class TimelineUtil {

    private static final Logger logger = LoggerFactory.getLogger(TimelineUtil.class);

    private static TaskTimelineService taskTimelineService;

    private static TaskService taskService;

    public TimelineUtil(TaskTimelineService taskTimelineService, TaskService taskService){
        TimelineUtil.taskTimelineService = taskTimelineService;
        TimelineUtil.taskService = taskService;
    }

    public static JSONObject addActivityToTimeline(TaskTimeline taskTimeline) {
        JSONObject response = new JSONObject();
        taskTimelineService.addActivity(taskTimeline);
        logger.info(">>> \"{}\" Activity added to timeline for task {}", taskTimeline.getMessage(), taskTimeline.getTask().getTaskId());
        response.put("Timeline", "Timeline Activity added successfully");
        return response;
    }

    public static JSONObject getTimelineActivities(Long taskId){
        JSONObject response = new JSONObject();
        response.put("Timeline", constructResponse(taskTimelineService.getActivitiesForTask(taskId)).get("result"));
        return response;
    }

    public static JSONObject addActivityToTimeline(String message, Long taskId){
        JSONObject response = new JSONObject();
        logger.info("Timeline activity saved successfully for taskId: {}", taskId);
        TaskTimeline taskTimeline = new TaskTimeline();
        taskTimeline.setMessage(message);
        taskTimeline.setCreatedTime(LocalDateTime.now());
        taskTimeline.setTask(taskService.getTaskById(taskId));
        taskTimelineService.addActivity(taskTimeline);
        response.put("Timeline", "Timeline activity saved successfully for taskId: " + taskId);
        return response;
    }

    private static JSONObject constructResponse(List<TaskTimeline> taskTimelineList){
        JSONObject response = new JSONObject();

        JSONArray timelineArr = new JSONArray();
        for(TaskTimeline taskTimeline : taskTimelineList){
            JSONObject taskTimelineObj = new JSONObject();
            taskTimelineObj.put("activityId", taskTimeline.getActivityId());
            taskTimelineObj.put("message", taskTimeline.getMessage());
            taskTimelineObj.put("createdTime", taskTimeline.getCreatedTime());
            taskTimelineObj.put("taskId", taskTimeline.getTask().getTaskId());
            timelineArr.add(taskTimelineObj);
        }
        response.put("result", timelineArr);
        return response;
    }

}
