package com.taskswift.main.dao;

import com.taskswift.main.entity.Notification;
import com.taskswift.main.entity.User;

import java.util.List;

public interface NotificationDao {

    public List<Notification> getAllNotification();

    public List<Notification> getAllNotificationForUser(User user);

    public Notification getNotificationById(Long notificationId);

    public void saveNotification(Notification notification);


}
