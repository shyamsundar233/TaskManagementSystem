package com.taskswift.main.dao;

import com.taskswift.main.entity.Notification;
import com.taskswift.main.entity.User;
import com.taskswift.main.repo.NotificationRepo;
import com.taskswift.main.util.TenantUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class NotificationDaoImpl implements NotificationDao{

    @Autowired
    private NotificationRepo notificationRepo;

    @Override
    public List<Notification> getAllNotification() {
        return notificationRepo.findAllByNotificationIdIsBetween(TenantUtil.currentTenant.getStartRange(), TenantUtil.currentTenant.getEndRange());
    }

    @Override
    public List<Notification> getAllNotificationForUser(User user) {
        return notificationRepo.findAllByNotificationIdIsBetweenAndIsNotfForAllIsTrueOrUser(TenantUtil.currentTenant.getStartRange(), TenantUtil.currentTenant.getEndRange(), user);
    }

    @Override
    public Notification getNotificationById(Long notificationId) {
        return notificationRepo.findByNotificationIdAndNotificationIdIsBetween(notificationId, TenantUtil.currentTenant.getStartRange(), TenantUtil.currentTenant.getEndRange());
    }

    @Override
    public void saveNotification(Notification notification) {
        notificationRepo.save(notification);
    }
}
