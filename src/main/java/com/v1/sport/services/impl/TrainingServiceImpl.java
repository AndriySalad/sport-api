package com.v1.sport.services.impl;

import com.v1.sport.Exceptions.EntityNotFoundException;
import com.v1.sport.data.dto.TrainingAdviceDto;
import com.v1.sport.data.dto.TrainingDto;
import com.v1.sport.data.models.TrainAdvice;
import com.v1.sport.data.models.Training;
import com.v1.sport.data.models.User;
import com.v1.sport.repository.TrainingAdviceRepository;
import com.v1.sport.repository.TrainingRepository;
import com.v1.sport.repository.UserRepository;
import com.v1.sport.services.TrainingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TrainingServiceImpl implements TrainingService {

    private final UserRepository userRepository;
    private final TrainingRepository trainingRepository;
    private final TrainingAdviceRepository trainingAdviceRepository;

    @Override
    @Transactional
    public TrainingDto initTraining(Long athleteId) {
        User athlete = userRepository.findById(athleteId).orElseThrow(() -> new EntityNotFoundException("User not found"));

        Training training = Training.builder()
                .user(athlete)
                .isPublished(false)
                .isDone(false)
                .build();
        Training savedTraining = trainingRepository.save(training);
        return mapToDto(savedTraining);
    }

    @Override
    @Transactional
    public TrainingDto updateTraining(Long trainingId, TrainingDto trainingDto) {
        Training training = trainingRepository.findById(trainingId)
                .orElseThrow(() -> new EntityNotFoundException("Training not found"));
        training.setDate(trainingDto.getDate());
        training.setDescription(trainingDto.getDescription());
        training.setTitle(trainingDto.getTitle());
        training.setDone(trainingDto.isDone());
        updateTrainAdvices(training, trainingDto);
        Training savedTraining = trainingRepository.save(training);
        return mapToDto(savedTraining);
    }

    private void updateTrainAdvices(Training training, TrainingDto trainingDto) {
        Set<TrainingAdviceDto> advices = trainingDto.getAdvices();
        if (advices != null) {
            Set<TrainAdvice> advicesBefore = training.getAdvices();

            Set<TrainAdvice> trainAdvices =  advices.stream()
                    .map(advice -> {
                        TrainAdvice trainAdvice = trainingAdviceRepository.findById(advice.getId())
                                .orElseThrow(() -> new EntityNotFoundException("Advice not found"));
                        trainAdvice.setTitle(advice.getTitle());
                        trainAdvice.setDescription(advice.getDescription());
                        trainAdvice.setTraining(training);
                        trainingAdviceRepository.save(trainAdvice);
                        return trainAdvice;
            }).collect(Collectors.toSet());
            training.setAdvices(trainAdvices);

            for (TrainAdvice advice : advicesBefore) {
                if (!trainAdvices.contains(advice)) {
                    trainingAdviceRepository.delete(advice);
                }
            }
        }
    }

    @Override
    @Transactional
    public TrainingAdviceDto initTrainingAdvice(Long trainingId) {
        Training training = trainingRepository.findById(trainingId)
                .orElseThrow(() -> new EntityNotFoundException("Training not found"));
        TrainAdvice advice = TrainAdvice.builder()
                .training(training)
                .build();
        training.getAdvices().add(advice);
        trainingRepository.save(training);
        TrainAdvice savedAdvice = trainingAdviceRepository.save(advice);
        return TrainingAdviceDto.builder()
                .id(savedAdvice.getId())
                .build();
    }

    @Override
    public TrainingDto getTraining(Long trainingId) {
        Training training = trainingRepository.findById(trainingId)
                .orElseThrow(() -> new EntityNotFoundException("Training not found"));
        return mapToDto(training);
    }

    @Override
    public List<TrainingDto> getTrainingsByAthlete(Long athleteId) {
        List<Training> trainings = trainingRepository.findAllByUserId(athleteId);
        return trainings.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public TrainingDto publishTraining(Long trainingId) {
        Training training = trainingRepository.findById(trainingId)
                .orElseThrow(() -> new EntityNotFoundException("Training not found"));
        training.setPublished(true);
        Training savedTraining = trainingRepository.save(training);
        return mapToDto(savedTraining);
    }

    @Override
    public TrainingDto markTrainingAsDone(Long trainingId) {
        Training training = trainingRepository.findById(trainingId)
                .orElseThrow(() -> new EntityNotFoundException("Training not found"));
        training.setDone(true);
        Training savedTraining = trainingRepository.save(training);
        return mapToDto(savedTraining);
    }

    private TrainingDto mapToDto(Training training) {
        return TrainingDto.builder()
                .id(training.getId())
                .title(training.getTitle())
                .description(training.getDescription())
                .date(training.getDate())
                .isDone(training.isDone())
                .advices(training.getAdvices() != null ? advicesToDto(training.getAdvices()) : null)
                .isPublished(training.isPublished())
                .build();
    }

    private Set<TrainingAdviceDto> advicesToDto(Set<TrainAdvice> advices) {
        return advices.stream()
                .map(advice -> TrainingAdviceDto.builder()
                        .id(advice.getId())
                        .title(advice.getTitle())
                        .description(advice.getDescription())
                        .build())
                .collect(Collectors.toSet());
    }
}
