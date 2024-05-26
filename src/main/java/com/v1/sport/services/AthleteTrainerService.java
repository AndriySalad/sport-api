package com.v1.sport.services;

import com.v1.sport.data.dto.TraineeDto;
import com.v1.sport.data.dto.TrainerDto;
import com.v1.sport.data.dto.UserListItemDto;
import com.v1.sport.data.models.User;

import java.util.List;

public interface AthleteTrainerService {
    
    List<TraineeDto> getTraineesByTrainer(Long trainerId);

    TrainerDto getTrainerByTrainee(Long athleteId);

    List<UserListItemDto> getAllTrainees();

    List<UserListItemDto> getAllTrainers();

    UserListItemDto mapToDto(User user);

    void createRequest(Long athleteId, Long trainerId, String type);

    void handleRequest(Long athleteId, Long trainerId, String action);
}
