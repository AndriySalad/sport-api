package com.v1.sport.services;

import com.v1.sport.data.dto.NotificationDto;

import java.util.List;

public interface NotificationService {

    List<NotificationDto> getNewNotifications();

    List<NotificationDto> getAllNotifications();

    NotificationDto markNotificationAsRead(Long notificationId);

    List<NotificationDto> markAllNotificationsAsRead(Long userId);

    NotificationDto handleRequestResponse(Long notificationId, String response);
}
