package com.taskswift.main.util;

import com.taskswift.main.controller.MessagingController;
import com.taskswift.main.entity.Notification;
import com.taskswift.main.model.Message;
import com.taskswift.main.service.NotificationService;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Component;

import java.util.List;

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
        response.put("Notification", constructJsonForNotf(notificationService.getAllNotificationForUser(UserUtil.currentUser)).get("result"));
        return response;
    }

    public static JSONObject markAsRead(List<Long> notificationIds) {
        JSONObject response = new JSONObject();
        for(Long notificationId : notificationIds){
            markAsRead(notificationId);
        }
        response.put("Notification", "Notifications marked as read successfully");
        return response;
    }

    private static JSONObject constructJsonForNotf(List<Notification> notfList) {
        JSONObject response = new JSONObject();
        JSONArray notfArray = new JSONArray();
        for(Notification notification : notfList) {
            JSONObject notfObj = new JSONObject();
            notfObj.put("message", notification.getMessage());
            notfObj.put("notificationId", notification.getNotificationId());
            notfObj.put("notfForAll", notification.isNotfForAll());
            notfObj.put("isRead", notification.isRead());
            notfArray.add(notfObj);
        }
        response.put("result", notfArray);
        return response;
    }

    private static void saveNotification(Message message) {
        Notification notification = new Notification();
        notification.setNotificationId(TenantUtil.getNextUniqueId());
        notification.setMessage(String.valueOf(message.getMessageBody()));
        notification.setNotfForAll(false);
        notification.setRead(false);
        notification.setUser(UserUtil.getUserByName(message.getTo()));
        notificationService.saveNotification(notification);
    }

    private static void markAsRead(Long notfId) {
        Notification notification = notificationService.getNotificationById(notfId);
        if(notification != null){
            notification.setRead(true);
            notificationService.saveNotification(notification);
        }
    }

}
