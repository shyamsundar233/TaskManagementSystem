package com.taskswift.main.controller;

import com.taskswift.main.entity.TaskTimeline;
import com.taskswift.main.util.TimelineUtil;
import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/api")
@CrossOrigin("http://localhost:3000")
public class TimelineController {

    @GetMapping("/timeline")
    public JSONObject getTimeline(){
        return null;
    }

    @GetMapping("/timeline/{taskId}")
    public JSONObject getTimelineForTaskId(@PathVariable Long taskId){
        return TimelineUtil.getTimelineActivities(taskId);
    }

    @PostMapping("/timeline")
    public JSONObject postTimeline(@RequestBody TaskTimeline taskTimeline){
        return TimelineUtil.addActivityToTimeline(taskTimeline);
    }

}
