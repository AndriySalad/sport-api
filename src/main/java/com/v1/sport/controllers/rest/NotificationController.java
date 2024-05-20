package com.v1.sport.controllers.rest;

import com.v1.sport.data.dto.NotificationDto;
import com.v1.sport.services.NotificationService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/notifications")
public class NotificationController {

    private final NotificationService notificationService;

    @GetMapping("/{userId}")
    public List<NotificationDto> getNewNotifications(@PathVariable("userId") Long userId){
        return notificationService.getNewNotifications(userId);
    }

    @GetMapping("/all/{userId}")
    public List<NotificationDto> getAllNotifications(@PathVariable("userId") Long userId){
        return notificationService.getAllNotifications(userId);
    }

    @PutMapping("/{notificationId}")
    public NotificationDto markNotificationAsRead(@PathVariable("notificationId") Long notificationId){
        return notificationService.markNotificationAsRead(notificationId);
    }

    @PutMapping("/all/{userId}")
    public List<NotificationDto> markAllNotificationsAsRead(@PathVariable("userId") Long userId){
        return notificationService.markAllNotificationsAsRead(userId);
    }

    @PostMapping("/{trainerId}/to/{athleteId}")
    public NotificationDto createNotificationAboutNewTrainings(@PathVariable("trainerId") Long trainerId, @PathVariable("athleteId") Long athleteId){
        return notificationService.createNotification(trainerId, athleteId);
    }
}
