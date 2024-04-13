package com.taskswift.main.controller;

import com.taskswift.main.util.NotificationUtil;
import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/api")
public class NotificationController {

    @GetMapping("/notification")
    public JSONObject getAllNotification() {
        return NotificationUtil.getAllNotifications();
    }

    @PostMapping("/markAsRead")
    public JSONObject postMarkAsRead(@RequestBody List<Long> notificationIds) {
        return NotificationUtil.markAsRead(notificationIds);
    }

}
