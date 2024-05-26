package com.v1.sport.services.impl;

import com.v1.sport.Exceptions.EntityNotFoundException;
import com.v1.sport.data.dto.TraineeDto;
import com.v1.sport.data.dto.TrainerDto;
import com.v1.sport.data.dto.UserListItemDto;
import com.v1.sport.data.models.Notification;
import com.v1.sport.data.models.NotificationType;
import com.v1.sport.data.models.Role;
import com.v1.sport.data.models.User;
import com.v1.sport.repository.NotificationRepository;
import com.v1.sport.repository.UserRepository;
import com.v1.sport.services.AthleteTrainerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AthleteTrainerServiceImpl implements AthleteTrainerService {

    private final UserRepository userRepository;
    private final NotificationRepository notificationRepository;

    @Override
    public void handleRequest(Long athleteId, Long trainerId, String action) {
        User athlete = userRepository.findById(athleteId)
                .orElseThrow(() -> new IllegalArgumentException("Athlete not found"));
        User trainer = userRepository.findById(trainerId)
                .orElseThrow(() -> new IllegalArgumentException("Trainer not found"));

        switch (action.toLowerCase()) {
            case "accept-trainee":
            case "accept-trainer":
                athlete.setTrainer(trainer);
                userRepository.save(athlete);
                createNotification(trainerId, athleteId, action.equals("accept-trainee") ? "ACCEPTED_TRAINEE" : "ACCEPTED_COACH");
                break;
            case "reject-trainee":
            case "reject-trainer":
                createNotification(trainerId, athleteId, action.equals("reject-trainee") ? "REJECTED_TRAINEE" : "ACCEPTED_COACH");
                break;
            case "remove-trainee":
            case "remove-trainer":
                athlete.setTrainer(null);
                userRepository.save(athlete);
                createNotification(trainerId, athleteId, action.equals("remove-trainee") ? "REMOVED_TRAINEE" : "ACCEPTED_COACH");
                break;
            default:
                throw new IllegalArgumentException("Invalid action: " + action);
        }
    }

    @Override
    public void createRequest(Long athleteId, Long trainerId, String type) {
        createNotification(trainerId, athleteId, type);
    }

    private void createNotification(Long senderId, Long receiverId, String type) {
        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new IllegalArgumentException("Sender not found"));
        User receiver = userRepository.findById(receiverId)
                .orElseThrow(() -> new IllegalArgumentException("Receiver not found"));

        Notification notification = new Notification();
        notification.setSender(sender);
        notification.setReceiver(receiver);
        notification.setType(NotificationType.valueOf(type));
        notification.setViewed(false);
        notification.setDate(new Timestamp(System.currentTimeMillis()));

        notificationRepository.save(notification);
    }



    @Override
    public List<TraineeDto> getTraineesByTrainer(Long trainerId) {
        User trainer = userRepository.findById(trainerId)
                .orElseThrow(() -> new EntityNotFoundException("Trainer not found"));

        List<User> trainees = userRepository.findAllByTrainer(trainer);

        return trainees.stream()
                .map(this::mapToTraineeDto)
                .toList();
    }

    private TraineeDto mapToTraineeDto(User user) {
        return TraineeDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .build();
    }

    @Override
    public TrainerDto getTrainerByTrainee(Long athleteId) {
        User athlete = userRepository.findById(athleteId)
                .orElseThrow(() -> new EntityNotFoundException("Athlete not found"));
        User trainer = athlete.getTrainer();

        return mapToTrainerDto(trainer);
    }

    private TrainerDto mapToTrainerDto(User user) {
        return TrainerDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .build();
    }

    @Override
    public List<UserListItemDto> getAllTrainees() {
        List<User> trainees = userRepository.findAllByRole(Role.ROLE_USER);
        return trainees.stream()
                .map(this::mapToDto)
                .toList();
    }

    @Override
    public List<UserListItemDto> getAllTrainers() {
        List<User> trainers = userRepository.findAllByRole(Role.ROLE_TRAINER);
        return trainers.stream()
                .map(this::mapToDto)
                .toList();
    }

    @Override
    public UserListItemDto mapToDto(User user) {
        return UserListItemDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .userName(user.getName())
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();
    }
}
