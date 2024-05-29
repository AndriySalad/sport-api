package com.v1.sport.services.impl;

import com.v1.sport.Exceptions.EntityNotFoundException;
import com.v1.sport.data.dto.SocialMediaLinkDto;
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
                if (athlete.getRole().equals(Role.ROLE_TRAINER)) {
                    trainer.setTrainer(athlete);
                    userRepository.save(athlete);
                    createNotification(athleteId, trainerId, "ACCEPTED_ATHLETE");
                }
                else{
                    athlete.setTrainer(trainer);
                    userRepository.save(athlete);
                    createNotification(trainerId, athleteId, "ACCEPTED_ATHLETE");
                }
                break;
            case "accept-trainer":
                if (athlete.getRole().equals(Role.ROLE_TRAINER)) {
                    trainer.setTrainer(athlete);
                    userRepository.save(athlete);
                    createNotification(trainerId, athleteId, "ACCEPTED_COACH");
                }
                else{
                    athlete.setTrainer(trainer);
                    userRepository.save(athlete);
                    createNotification(athleteId, trainerId, "ACCEPTED_COACH");
                }
                break;
            case "reject-trainee":
                createNotification(trainerId, athleteId, "REJECTED_ATHLETE");
                break;
            case "reject-trainer":
                createNotification(athleteId, trainerId, "REJECTED_COACH");
                break;
            case "remove-trainee":
                if (athlete.getRole().equals(Role.ROLE_TRAINER)) {
                    trainer.setTrainer(null);
                    userRepository.save(athlete);
                    createNotification(trainerId, athleteId, "REMOVED_ATHLETE");
                }
                else {
                    athlete.setTrainer(null);
                    userRepository.save(athlete);
                    createNotification(athleteId, trainerId, "REMOVED_ATHLETE");
                }
                break;
            case "remove-trainer":
                if (athlete.getRole().equals(Role.ROLE_TRAINER)) {
                    trainer.setTrainer(null);
                    userRepository.save(athlete);
                    createNotification(trainerId, athleteId, "REMOVED_COACH");
                }
                else {
                    athlete.setTrainer(null);
                    userRepository.save(athlete);
                    createNotification(athleteId, trainerId, "REMOVED_COACH");
                }
                break;
            default:
                throw new IllegalArgumentException("Invalid action: " + action);
        }
    }

    @Override
    public List<TraineeDto> getAthletesByTrainer(Long trainerId) {
        User trainer = userRepository.findById(trainerId)
                .orElseThrow(() -> new EntityNotFoundException("Trainer not found"));
        List<User> athletes = userRepository.findAllByTrainer(trainer);
        return athletes.stream()
                .map(this::mapToTraineeDto)
                .toList();
    }

    @Override
    public void createRequest(Long senderId, Long receiverId, String type) {
        createNotification(senderId, receiverId, type);
    }

    private void createNotification(Long senderId, Long receiverId, String type) {
        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new IllegalArgumentException("Sender not found"));
        User receiver = userRepository.findById(receiverId)
                .orElseThrow(() -> new IllegalArgumentException("Receiver not found"));

        String title = "";
        String description = "";

        switch (type) {
            case "TO_BE_COACH":
                title = "New coach request";
                description = "You have a new coach request from " + sender.getName();
                break;
            case "TO_BE_ATHLETE":
                title = "New athlete request";
                description = "You have a new athlete request from " + sender.getName();
                break;
            case "ACCEPTED_COACH":
                title = "Coach request accepted";
                description = "Your coach request has been accepted by " + receiver.getName();
                break;
            case "ACCEPTED_ATHLETE":
                title = "Athlete request accepted";
                description = "Your athlete request has been accepted by " + receiver.getName();
                break;
            case "REJECTED_COACH":
                title = "Coach request rejected";
                description = "Your coach request has been rejected by " + receiver.getName();
                break;
            case "REJECTED_ATHLETE":
                title = "Athlete request rejected";
                description = "Your athlete request has been rejected by " + receiver.getName();
                break;
            case "REMOVED_COACH":
                title = "Coach removed";
                description = "You have been removed as a coach by " + receiver.getName();
                break;
            case "REMOVED_ATHLETE":
                title = "Athlete removed";
                description = "You have been removed as an athlete by " + receiver.getName();
                break;
            default:
                throw new IllegalArgumentException("Invalid action: " + type);
        }

        Notification notification = new Notification();
        notification.setSender(sender);
        notification.setReceiver(receiver);
        notification.setType(NotificationType.valueOf(type));
        notification.setTitle(title);
        notification.setDescription(description);
        notification.setViewed(false);
        notification.setDate(new Timestamp(System.currentTimeMillis()));

        notificationRepository.save(notification);
    }

    @Override
    public TraineeDto getAthlete(Long trainerId) {
        User athlete = userRepository.findById(trainerId)
                .orElseThrow(() -> new EntityNotFoundException("Trainer not found"));

        return mapToTraineeDto(athlete);
    }

    private TraineeDto mapToTraineeDto(User user) {
        return TraineeDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phone(user.getPhoneNumber())
                .trainerId(user.getTrainer() != null ? user.getTrainer().getId() : null)
                .goalDescription(user.getGoalDescription())
                .experienceDescription(user.getExperienceDescription())
                .injuryDescription(user.getInjuryDescription())
                .role(user.getRole().name())
                .socialMediaLinks(user.getSocialMediaLinks().stream()
                        .map(socialMediaLink -> SocialMediaLinkDto.builder()
                                .id(socialMediaLink.getId())
                                .link(socialMediaLink.getLink())
                                .title(socialMediaLink.getTitle())
                                .build())
                        .toList())
                .build();
    }

    @Override
    public TrainerDto getTrainer(Long trainerId) {
        User trainer = userRepository.findById(trainerId)
                .orElseThrow(() -> new EntityNotFoundException("Trainer not found"));

        return mapToTrainerDto(trainer);
    }

    private TrainerDto mapToTrainerDto(User user) {
        return TrainerDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .phone(user.getPhoneNumber())
                .goalDescription(user.getGoalDescription())
                .experienceDescription(user.getExperienceDescription())
                .injuryDescription(user.getInjuryDescription())
                .role(user.getRole().name())
                .socialMediaLinks(user.getSocialMediaLinks().stream()
                        .map(socialMediaLink -> SocialMediaLinkDto.builder()
                                .id(socialMediaLink.getId())
                                .link(socialMediaLink.getLink())
                                .title(socialMediaLink.getTitle())
                                .build())
                        .toList())
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
