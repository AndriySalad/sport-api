package com.v1.sport.controllers.rest;

import com.v1.sport.data.dto.ExerciseDto;
import com.v1.sport.data.dto.TrainingDto;
import com.v1.sport.services.TrainingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/trainings")
public class TrainingController {

    private final TrainingService trainingService;

    @PostMapping("/{athleteId}")
    public TrainingDto createTraining(@PathVariable("athleteId") Long athleteId, @RequestBody TrainingDto trainingDto) {
        return trainingService.initTraining(athleteId, trainingDto);
    }

    @PutMapping("/{trainingId}")
    public TrainingDto updateTraining(@PathVariable("trainingId") Long trainingId, @RequestBody TrainingDto trainingDto) {
        return trainingService.updateTraining(trainingId, trainingDto);
    }

    @GetMapping("/by-athlete/{athleteId}/between/{startDate}/and/{endDate}")
    public List<TrainingDto> getTrainingsByAthlete(@PathVariable("athleteId") Long athleteId,
                                                   @PathVariable("startDate") String startDate,
                                                   @PathVariable("endDate") String endDate) {
        return trainingService.getTrainingsByAthlete(athleteId, startDate, endDate);
    }

    @PostMapping("/{trainingId}/exercises")
    public ExerciseDto createExercise(@PathVariable("trainingId") Long trainingId, @RequestBody ExerciseDto exerciseDto) {
        return trainingService.createExercise(trainingId, exerciseDto);
    }

    @PutMapping("/is-done/{trainingId}/{exerciseId}")
    public ExerciseDto markExerciseAsDone(@PathVariable("trainingId") Long trainingId,
                                          @PathVariable("exerciseId") Long exerciseId) {
        return trainingService.markExerciseAsDone(trainingId, exerciseId);
    }

    @PutMapping("/{trainingId}/advices/{adviceId}")
    public ExerciseDto updateTrainingAdvice(@PathVariable("trainingId") Long trainingId,
                                            @PathVariable("adviceId") Long adviceId,
                                            @RequestBody ExerciseDto exerciseDto) {
        return trainingService.updateExercise(exerciseDto, trainingId, adviceId);
    }
}
