package com.v1.sport.services.impl;

import com.v1.sport.Exceptions.EntityNotFoundException;
import com.v1.sport.data.dto.NotificationDto;
import com.v1.sport.data.models.Notification;
import com.v1.sport.data.models.User;
import com.v1.sport.repository.NotificationRepository;
import com.v1.sport.repository.UserRepository;
import com.v1.sport.services.AthleteTrainerService;
import com.v1.sport.services.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final UserRepository userRepository;
    private final NotificationRepository notificationRepository;
    private final AthleteTrainerService athleteTrainerService;

    @Override
    public List<NotificationDto> getNewNotifications() {
        User user = userRepository
                .findByEmail(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        List<Notification> newNotifications = notificationRepository.findAllByReceiverAndIsViewed(user, false);
        List<NotificationDto> notificationDtos = newNotifications.stream().map(this::mapToDto).collect(Collectors.toList());
        notificationDtos.sort((n1, n2) -> n2.getDate().compareTo(n1.getDate()));

        return notificationDtos;
    }

    @Override
    public List<NotificationDto> getAllNotifications() {
        User user = userRepository
                .findByEmail(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        List<Notification> notifications = notificationRepository.findAllByReceiverId(user.getId());
        List<NotificationDto> notificationDtos = notifications.stream().map(this::mapToDto).collect(Collectors.toList());
        notificationDtos.sort((n1, n2) -> n2.getDate().compareTo(n1.getDate()));
        return notificationDtos;
    }

    @Override
    public NotificationDto markNotificationAsRead(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new IllegalArgumentException("Notification not found"));
        notification.setViewed(true);
        notificationRepository.save(notification);
        return mapToDto(notification);
    }

    @Override
    public List<NotificationDto> markAllNotificationsAsRead(Long userId) {
        List<Notification> notifications = notificationRepository.findByReceiverId(userId);
        notifications.forEach(notification -> notification.setViewed(true));
        notificationRepository.saveAll(notifications);
        return notifications.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public NotificationDto handleRequestResponse(Long notificationId, String response) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new IllegalArgumentException("Notification not found"));

        Long receiverId = notification.getReceiver().getId();
        Long senderId = notification.getSender().getId();

        athleteTrainerService.handleRequest(senderId, receiverId, response);

        notification.setViewed(true);
        notificationRepository.save(notification);

        return mapToDto(notification);
    }

    private NotificationDto mapToDto(Notification notification) {
        return NotificationDto.builder()
                .id(notification.getId())
                .sender(notification.getSender() != null ? athleteTrainerService.mapToDto(notification.getSender()) : null)
                .receiver(notification.getReceiver() != null ? athleteTrainerService.mapToDto(notification.getReceiver()) : null)
                .isViewed(notification.isViewed())
                .type(notification.getType().toString())
                .date(notification.getDate())
                .description(notification.getDescription())
                .title(notification.getTitle())
                .build();
    }
}
