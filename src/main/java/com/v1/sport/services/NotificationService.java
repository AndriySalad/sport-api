package com.v1.sport.services;

import com.v1.sport.data.dto.NotificationDto;

import java.util.List;

public interface NotificationService {

    List<NotificationDto> getNewNotifications(Long userId);

    List<NotificationDto> getAllNotifications(Long userId);

    NotificationDto markNotificationAsRead(Long notificationId);

    List<NotificationDto> markAllNotificationsAsRead(Long userId);

    NotificationDto createNotification(Long senderId, Long receiverId);
}
