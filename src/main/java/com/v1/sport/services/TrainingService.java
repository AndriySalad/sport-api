package com.v1.sport.services;

import com.v1.sport.data.dto.ExerciseDto;
import com.v1.sport.data.dto.TrainingDto;
import com.v1.sport.data.models.Exercise;
import com.v1.sport.data.models.Training;

import java.util.List;

public interface TrainingService {

    TrainingDto initTraining(Long athleteId, TrainingDto trainingDto);

    TrainingDto updateTraining(Long trainingId, TrainingDto trainingDto);

    ExerciseDto createExercise(Long trainingId, ExerciseDto exerciseDto);

    List<TrainingDto> getTrainingsByAthlete(Long athleteId, String startDate, String endDate);

    ExerciseDto markExerciseAsDone(Long trainingId, Long exerciseId);

    ExerciseDto updateExercise(ExerciseDto exerciseDto, Long trainingId, Long adviceId);

    TrainingDto mapToDto(Training training);
}
