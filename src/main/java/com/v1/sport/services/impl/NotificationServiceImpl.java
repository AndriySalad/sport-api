package com.v1.sport.services.impl;

import com.v1.sport.Exceptions.EntityNotFoundException;
import com.v1.sport.data.dto.NotificationDto;
import com.v1.sport.data.models.Notification;
import com.v1.sport.data.models.NotificationType;
import com.v1.sport.data.models.User;
import com.v1.sport.repository.NotificationRepository;
import com.v1.sport.repository.UserRepository;
import com.v1.sport.services.AthleteTrainerService;
import com.v1.sport.services.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {

    private final UserRepository userRepository;
    private final NotificationRepository notificationRepository;
    private final AthleteTrainerService athleteTrainerService;

    @Override
    public List<NotificationDto> getNewNotifications(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
        List<Notification> newNotifications = notificationRepository.findAllByReceiverAndIsViewed(user, false);
        List<NotificationDto> notificationDtos = newNotifications.stream().map(this::mapToDto).collect(Collectors.toList());
        notificationDtos.sort((n1, n2) -> n2.getDate().compareTo(n1.getDate()));

        return notificationDtos;
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

    @Override
    public List<NotificationDto> getAllNotifications(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
        List<Notification> notifications = notificationRepository.findAllByReceiverAndIsViewed(user, true);
        List<NotificationDto> notificationDtos = notifications.stream().map(this::mapToDto).collect(Collectors.toList());
        notificationDtos.sort((n1, n2) -> n2.getDate().compareTo(n1.getDate()));
        return notificationDtos;
    }

    @Override
    public NotificationDto markNotificationAsRead(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId).orElseThrow(() -> new EntityNotFoundException("Notification not found"));
        notification.setViewed(true);
        Notification savedNotification = notificationRepository.save(notification);
        return mapToDto(savedNotification);
    }

    @Override
    public List<NotificationDto> markAllNotificationsAsRead(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found"));
        List<Notification> notifications = notificationRepository.findAllByReceiverAndIsViewed(user, false);
        notifications.forEach(notification -> notification.setViewed(true));
        List<Notification> savedNotifications = notificationRepository.saveAll(notifications);
        List<NotificationDto> notificationDtos = savedNotifications.stream().map(this::mapToDto).collect(Collectors.toList());
        notificationDtos.sort((n1, n2) -> n2.getDate().compareTo(n1.getDate()));
        return notificationDtos;
    }

    @Override
    @Transactional
    public NotificationDto createNotification(Long senderId, Long receiverId) {
        User sender = userRepository.findById(senderId).orElseThrow(() -> new EntityNotFoundException("Sender not found"));
        User receiver = userRepository.findById(receiverId).orElseThrow(() -> new EntityNotFoundException("Receiver not found"));

        Notification notification = Notification.builder()
                .sender(sender)
                .receiver(receiver)
                .isViewed(false)
                .type(NotificationType.NEW_TRAININGS)
                .description("The trainer has created a new training for you. Check it out!")
                .title("New training")
                .date(new Timestamp(System.currentTimeMillis()))
                .build();
        Notification savedNotification = notificationRepository.save(notification);

        return mapToDto(savedNotification);
    }
}
