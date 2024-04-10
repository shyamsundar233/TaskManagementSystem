package com.taskswift.main.controller;

import com.taskswift.main.util.NotificationUtil;
import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/api")
public class NotificationController {

    @GetMapping("/notification")
    public JSONObject getAllNotification() {
        return NotificationUtil.getAllNotifications();
    }

}
