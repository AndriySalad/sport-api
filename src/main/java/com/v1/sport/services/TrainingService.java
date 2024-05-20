package com.v1.sport.services;

import com.v1.sport.data.dto.TrainingAdviceDto;
import com.v1.sport.data.dto.TrainingDto;

import java.util.List;

public interface TrainingService {

    TrainingDto initTraining(Long athleteId);

    TrainingDto updateTraining(Long trainingId, TrainingDto trainingDto);

    TrainingAdviceDto initTrainingAdvice(Long trainingId);
    TrainingDto getTraining(Long trainingId);
    List<TrainingDto> getTrainingsByAthlete(Long athleteId);


    TrainingDto publishTraining(Long trainingId);

    TrainingDto markTrainingAsDone(Long trainingId);
}
