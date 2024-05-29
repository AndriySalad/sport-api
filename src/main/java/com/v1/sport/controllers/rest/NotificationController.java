package com.v1.sport.controllers.rest;

import com.v1.sport.data.dto.NotificationDto;
import com.v1.sport.services.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping()
    public List<NotificationDto> getNewNotifications() {
        return notificationService.getNewNotifications();
    }

    @GetMapping("/all")
    public List<NotificationDto> getAllNotifications() {
        return notificationService.getAllNotifications();
    }

    @PutMapping("/{notificationId}")
    public NotificationDto markNotificationAsRead(@PathVariable("notificationId") Long notificationId) {
        return notificationService.markNotificationAsRead(notificationId);
    }

    @PutMapping("/all/{userId}")
    public List<NotificationDto> markAllNotificationsAsRead(@PathVariable("userId") Long userId) {
        return notificationService.markAllNotificationsAsRead(userId);
    }

    @PostMapping("/{notificationId}/response")
    public NotificationDto handleRequestResponse(@PathVariable("notificationId") Long notificationId, @RequestBody Map<String, String> response) {
        String action = response.get("response");
        return notificationService.handleRequestResponse(notificationId, action);
    }
}
