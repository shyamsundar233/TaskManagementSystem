package com.taskswift.main.service;

import com.taskswift.main.dao.NotificationDao;
import com.taskswift.main.entity.Notification;
import com.taskswift.main.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService{

    @Autowired
    private NotificationDao notificationDao;

    @Override
    public List<Notification> getAllNotification() {
        return notificationDao.getAllNotification();
    }

    @Override
    public List<Notification> getAllNotificationForUser(User user) {
        return notificationDao.getAllNotificationForUser(user);
    }

    @Override
    public void saveNotification(Notification notification) {
        notificationDao.saveNotification(notification);
    }
}
