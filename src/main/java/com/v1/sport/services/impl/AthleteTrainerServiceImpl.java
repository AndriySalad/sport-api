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
    public void toBeTrainee(Long athleteId, Long trainerId) {
        User athlete = userRepository.findById(athleteId)
                .orElseThrow(() -> new EntityNotFoundException("Athlete not found"));
        User trainer = userRepository.findById(trainerId)
                .orElseThrow(() -> new EntityNotFoundException("Trainer not found"));

        Notification notification = Notification.builder()
                .title("You have a new trainee")
                .description("User " + athlete.getFirstName() + " " + athlete.getLastName() +
                        " wants you to train him. " +
                        "Take a quick look at his profile and decide " +
                        "whether you want to accept him or not.")
                .date(new Timestamp(System.currentTimeMillis()))
                .sender(athlete)
                .receiver(trainer)
                .type(NotificationType.TO_BE_ATHLETE)
                .build();

        notificationRepository.save(notification);
    }

    @Override
    public void toBeTrainer(Long athleteId, Long trainerId) {
        User athlete = userRepository.findById(athleteId)
                .orElseThrow(() -> new EntityNotFoundException("Athlete not found"));
        User trainer = userRepository.findById(trainerId)
                .orElseThrow(() -> new EntityNotFoundException("Trainer not found"));

        Notification notification = Notification.builder()
                .title("You have a new trainee")
                .description("User " + trainer.getFirstName() + " " + trainer.getLastName() +
                        " wants to be your coach. " +
                        "Take a quick look at his profile and decide " +
                        "whether you want to accept him or not.")
                .date(new Timestamp(System.currentTimeMillis()))
                .sender(trainer)
                .receiver(athlete)
                .type(NotificationType.TO_BE_COACH)
                .build();

        notificationRepository.save(notification);
    }

    @Override
    public void acceptTrainee(Long athleteId, Long trainerId) {
        User athlete = userRepository.findById(athleteId)
                .orElseThrow(() -> new EntityNotFoundException("Athlete not found"));
        User trainer = userRepository.findById(trainerId)
                .orElseThrow(() -> new EntityNotFoundException("Trainer not found"));

        if (!trainer.getRole().equals(Role.ROLE_TRAINER)) {
            throw new IllegalArgumentException("Invalid roles for trainer or athlete");
        }

        athlete.setTrainer(trainer);
        userRepository.save(athlete);

        Notification notification = Notification.builder()
                .title("You have a new trainee")
                .description("The coach " + trainer.getFirstName() + " " + trainer.getLastName() +
                        " has accepted your request.")
                .date(new Timestamp(System.currentTimeMillis()))
                .sender(trainer)
                .receiver(athlete)
                .type(NotificationType.ACCEPTED_ATHLETE)
                .build();

        notificationRepository.save(notification);
    }

    @Override
    public void rejectTrainee(Long athleteId, Long trainerId) {
        User athlete = userRepository.findById(athleteId)
                .orElseThrow(() -> new EntityNotFoundException("Athlete not found"));
        User trainer = userRepository.findById(trainerId)
                .orElseThrow(() -> new EntityNotFoundException("Trainer not found"));

        Notification notification = Notification.builder()
                .title("You have a new trainee")
                .description("The coach " + trainer.getFirstName() + " " + trainer.getLastName() +
                        " rejected your request.")
                .date(new Timestamp(System.currentTimeMillis()))
                .sender(trainer)
                .receiver(athlete)
                .type(NotificationType.REJECTED_ATHLETE)
                .build();

        notificationRepository.save(notification);
    }

    @Override
    public void removeTrainee(Long athleteId, Long trainerId) {
        User athlete = userRepository.findById(athleteId)
                .orElseThrow(() -> new EntityNotFoundException("Athlete not found"));
        User trainer = userRepository.findById(trainerId)
                .orElseThrow(() -> new EntityNotFoundException("Trainer not found"));

        athlete.setTrainer(null);

        Notification notification = Notification.builder()
                .title("You have a new trainee")
                .description("The coach " + trainer.getFirstName() + " " + trainer.getLastName() +
                        " will no longer train you")
                .date(new Timestamp(System.currentTimeMillis()))
                .sender(trainer)
                .receiver(athlete)
                .type(NotificationType.REMOVED_ATHLETE)
                .build();

        notificationRepository.save(notification);
    }

    @Override
    public void removeTrainer(Long athleteId, Long trainerId) {
        User athlete = userRepository.findById(athleteId)
                .orElseThrow(() -> new EntityNotFoundException("Athlete not found"));
        User trainer = userRepository.findById(trainerId)
                .orElseThrow(() -> new EntityNotFoundException("Trainer not found"));

        athlete.setTrainer(null);

        Notification notification = Notification.builder()
                .title("You have a new trainee")
                .description("The athlete " + athlete.getFirstName() + " " + athlete.getLastName() +
                        " will no longer train with you.")
                .date(new Timestamp(System.currentTimeMillis()))
                .sender(athlete)
                .receiver(trainer)
                .type(NotificationType.REMOVED_COACH)
                .build();

        notificationRepository.save(notification);
    }

    @Override
    public void acceptTrainer(Long athleteId, Long trainerId) {
        User athlete = userRepository.findById(athleteId)
                .orElseThrow(() -> new EntityNotFoundException("Athlete not found"));
        User trainer = userRepository.findById(trainerId)
                .orElseThrow(() -> new EntityNotFoundException("Trainer not found"));

        if (!trainer.getRole().equals(Role.ROLE_TRAINER)) {
            throw new IllegalArgumentException("Invalid roles for trainer or athlete");
        }

        athlete.setTrainer(trainer);
        userRepository.save(athlete);

        Notification notification = Notification.builder()
                .title("You have a new trainee")
                .description("The athlete " + athlete.getFirstName() + " " + athlete.getLastName() +
                        " has accepted your application to be a coach")
                .date(new Timestamp(System.currentTimeMillis()))
                .sender(athlete)
                .receiver(trainer)
                .type(NotificationType.ACCEPTED_COACH)
                .build();

        notificationRepository.save(notification);
    }

    @Override
    public void rejectTrainer(Long athleteId, Long trainerId) {
        User athlete = userRepository.findById(athleteId)
                .orElseThrow(() -> new EntityNotFoundException("Athlete not found"));
        User trainer = userRepository.findById(trainerId)
                .orElseThrow(() -> new EntityNotFoundException("Trainer not found"));

        Notification notification = Notification.builder()
                .title("You have a new trainee")
                .description("An athlete " + athlete.getFirstName() + " " + athlete.getLastName() +
                        " rejected your application to be a coach.")
                .date(new Timestamp(System.currentTimeMillis()))
                .sender(athlete)
                .receiver(trainer)
                .type(NotificationType.REJECTED_COACH)
                .build();

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
                .map(this::mapToUserListItemDto)
                .toList();
    }

    @Override
    public List<UserListItemDto> getAllTrainers() {
        List<User> trainers = userRepository.findAllByRole(Role.ROLE_TRAINER);
        return trainers.stream()
                .map(this::mapToUserListItemDto)
                .toList();
    }

    private UserListItemDto mapToUserListItemDto(User user) {
        return UserListItemDto.builder()
                .id(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .role(user.getRole().name())
                .build();
    }
}
