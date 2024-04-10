package com.taskswift.main.service;

import com.taskswift.main.entity.Notification;
import com.taskswift.main.entity.User;

import java.util.List;

public interface NotificationService {

    public List<Notification> getAllNotification();

    public List<Notification> getAllNotificationForUser(User user);

    public void saveNotification(Notification notification);

}
