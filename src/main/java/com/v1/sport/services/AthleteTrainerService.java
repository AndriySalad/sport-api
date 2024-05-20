package com.v1.sport.services;

import com.v1.sport.data.dto.TraineeDto;
import com.v1.sport.data.dto.TrainerDto;
import com.v1.sport.data.dto.UserListItemDto;
import com.v1.sport.data.models.User;

import java.util.List;

public interface AthleteTrainerService {

    void toBeTrainee(Long athleteId, Long trainerId);

    void toBeTrainer(Long athleteId, Long trainerId);

    void acceptTrainee(Long athleteId, Long trainerId);

    void rejectTrainee(Long athleteId, Long trainerId);

    void removeTrainee(Long athleteId, Long trainerId);

    void removeTrainer(Long athleteId, Long trainerId);

    void acceptTrainer(Long athleteId, Long trainerId);

    void rejectTrainer(Long athleteId, Long trainerId);

    List<TraineeDto> getTraineesByTrainer(Long trainerId);

    TrainerDto getTrainerByTrainee(Long athleteId);

    List<UserListItemDto> getAllTrainees();

    List<UserListItemDto> getAllTrainers();

    UserListItemDto mapToDto(User user);
}
