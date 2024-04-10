package com.taskswift.main.util;

import com.taskswift.main.controller.MessagingController;
import com.taskswift.main.entity.Notification;
import com.taskswift.main.model.Message;
import com.taskswift.main.service.NotificationService;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Component;

@Component
public class NotificationUtil {

    private static MessagingController messagingController;

    private static NotificationService notificationService;

    public NotificationUtil(MessagingController messagingController, NotificationService notificationService) {
        NotificationUtil.messagingController = messagingController;
        NotificationUtil.notificationService = notificationService;
    }

    public static void sendPublicMessage(Message message) throws Exception {
        messagingController.send(message);
    }

    public static void sendPrivateMessage(Message message) {
        saveNotification(message);
        messagingController.sendToSpecificUser(message);
    }

    public static JSONObject getAllNotifications() {
        JSONObject response = new JSONObject();
        response.put("Notification", notificationService.getAllNotificationForUser(UserUtil.currentUser));
        return response;
    }

    private static void saveNotification(Message message) {
        Notification notification = new Notification();
        notification.setNotificationId(TenantUtil.getNextUniqueId());
        notification.setMessage(String.valueOf(message.getMessageBody()));
        notification.setNotfForAll(false);
        notification.setUser(UserUtil.getUserByName(message.getTo()));
        notificationService.saveNotification(notification);
    }

}
