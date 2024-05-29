package com.v1.sport.services.impl;

import com.v1.sport.Exceptions.EntityNotFoundException;
import com.v1.sport.data.dto.ExerciseDto;
import com.v1.sport.data.dto.TrainingDto;
import com.v1.sport.data.models.Exercise;
import com.v1.sport.data.models.ExerciseType;
import com.v1.sport.data.models.Training;
import com.v1.sport.data.models.User;
import com.v1.sport.repository.ExerciseRepository;
import com.v1.sport.repository.TrainingRepository;
import com.v1.sport.repository.UserRepository;
import com.v1.sport.services.TrainingService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TrainingServiceImpl implements TrainingService {

    private final UserRepository userRepository;
    private final TrainingRepository trainingRepository;
    private final ExerciseRepository exerciseRepository;

    @Override
    @Transactional
    public TrainingDto initTraining(Long athleteId, TrainingDto trainingDto) {
        User athlete = userRepository.findById(athleteId).orElseThrow(() -> new EntityNotFoundException("User not found"));
        User creator = userRepository.findById(trainingDto.getCreatorId()).orElseThrow(() -> new EntityNotFoundException("User not found"));

        Training training = Training.builder()
                .user(athlete)
                .date(trainingDto.getDate())
                .description(trainingDto.getDescription())
                .title(trainingDto.getTitle())
                .creator(creator)
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
        Training savedTraining = trainingRepository.save(training);
        return mapToDto(savedTraining);
    }

    @Override
    public ExerciseDto updateExercise(ExerciseDto exerciseDto, Long trainingId, Long exerciseId) {
        Training training = trainingRepository.findById(trainingId)
                .orElseThrow(() -> new EntityNotFoundException("Training not found"));
        Exercise exercise = training.getExercises().stream()
                .filter(a -> a.getId().equals(exerciseId))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Advice not found"));
        exercise.setTitle(exerciseDto.getTitle());
        exercise.setDescription(exerciseDto.getDescription());
        exercise.setTraining(training);
        exercise.setCompleted(exerciseDto.getCompleted());
        exercise.setSets(exerciseDto.getSets());
        exercise.setRepetitions(exerciseDto.getRepetitions());
        exercise.setMeasurement(exercise.getMeasurement());
        exercise.setType(ExerciseType.valueOf(exerciseDto.getType()));
        Exercise savedExercise = exerciseRepository.save(exercise);
        return exerciseToDto(savedExercise);
    }

    @Override
    @Transactional
    public ExerciseDto createExercise(Long trainingId, ExerciseDto exerciseDto) {
        Training training = trainingRepository.findById(trainingId)
                .orElseThrow(() -> new EntityNotFoundException("Training not found"));
        Exercise advice = Exercise.builder()
                .training(training)
                .title(exerciseDto.getTitle())
                .description(exerciseDto.getDescription())
                .completed(exerciseDto.getCompleted())
                .sets(exerciseDto.getSets())
                .repetitions(exerciseDto.getRepetitions())
                .measurement(exerciseDto.getMeasurement())
                .type(ExerciseType.valueOf(exerciseDto.getType()))
                .build();
        training.getExercises().add(advice);
        trainingRepository.save(training);
        Exercise savedExercise = exerciseRepository.save(advice);
        return exerciseToDto(savedExercise);
    }

    @Override
    public List<TrainingDto> getTrainingsByAthlete(Long athleteId, String startDateStr, String endDateStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate startDate = LocalDate.parse(startDateStr, formatter);
        LocalDate endDate = LocalDate.parse(endDateStr, formatter);

        String startDateFinal = startDate.format(formatter);
        String endDateFinal = endDate.format(formatter);

        List<Training> trainings = trainingRepository.findAllByUserIdAndDateBetween(athleteId, startDateFinal, endDateFinal);
        return trainings.stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Override
    public ExerciseDto markExerciseAsDone(Long trainingId, Long exerciseId) {
        User user = userRepository
                .findByEmail(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        Training training = trainingRepository.findByIdAndUser(trainingId, user)
                .orElseThrow(() -> new EntityNotFoundException("Training not found"));
        Exercise exercise = exerciseRepository.findByIdAndTraining(exerciseId, training)
                .orElseThrow(() -> new EntityNotFoundException("Exercise not found"));
        exercise.setCompleted(true);
        Exercise savedExercise = exerciseRepository.save(exercise);
        return exerciseToDto(savedExercise);
    }

    @Override
    public TrainingDto mapToDto(Training training) {
        return TrainingDto.builder()
                .id(training.getId())
                .title(training.getTitle())
                .description(training.getDescription())
                .date(training.getDate())
                .exercises(training.getExercises() != null ? exercisesToDto(training.getExercises()) : null)
                .creatorId(training.getCreator() != null ?  training.getCreator().getId() : null)
                .build();
    }

    private Set<ExerciseDto> exercisesToDto(Set<Exercise> exercises) {
        return exercises.stream()
                .map(advice -> ExerciseDto.builder()
                        .id(advice.getId())
                        .title(advice.getTitle())
                        .description(advice.getDescription())
                        .completed(advice.getCompleted())
                        .sets(advice.getSets())
                        .repetitions(advice.getRepetitions())
                        .measurement(advice.getMeasurement())
                        .type(advice.getType().toString())
                        .build())
                .collect(Collectors.toSet());
    }

    private ExerciseDto exerciseToDto(Exercise exercise) {
        return  ExerciseDto.builder()
                        .id(exercise.getId())
                        .title(exercise.getTitle())
                        .description(exercise.getDescription())
                        .completed(exercise.getCompleted())
                        .sets(exercise.getSets())
                        .repetitions(exercise.getRepetitions())
                        .measurement(exercise.getMeasurement())
                        .type(exercise.getType().toString())
                        .build();

    }
}
