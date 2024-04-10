package com.taskswift.main.repo;

import com.taskswift.main.entity.Notification;
import com.taskswift.main.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NotificationRepo extends JpaRepository<Notification, Long> {

    List<Notification> findAllByNotificationIdIsBetween(Long startRange, Long endRange);

    List<Notification> findAllByNotificationIdIsBetweenAndIsNotfForAllIsTrueOrUser(Long startRange, Long endRange, User user);

}
