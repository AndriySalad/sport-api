package com.v1.sport.controllers.rest;

import com.v1.sport.data.dto.TrainingAdviceDto;
import com.v1.sport.data.dto.TrainingDto;
import com.v1.sport.data.models.Training;
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
    public TrainingDto initTraining(@PathVariable("athleteId") Long athleteId) {
        return trainingService.initTraining(athleteId);
    }

    @PutMapping("/{trainingId}")
    public TrainingDto updateTraining(@PathVariable("trainingId") Long trainingId, @RequestBody TrainingDto trainingDto) {
        return trainingService.updateTraining(trainingId, trainingDto);
    }

    @GetMapping("/{trainingId}")
    public TrainingDto getTraining(@PathVariable("trainingId") Long trainingId) {
        return trainingService.getTraining(trainingId);
    }

    @GetMapping("/by-athlete/{athleteId}")
    public List<TrainingDto> getTrainingsByAthlete(@PathVariable("athleteId") Long athleteId) {
        return trainingService.getTrainingsByAthlete(athleteId);
    }

    @PostMapping("/{trainingId}/advices")
    public TrainingAdviceDto initTrainingAdvice(@PathVariable("trainingId") Long trainingId) {
        return trainingService.initTrainingAdvice(trainingId);
    }

    @PutMapping("/publish/{trainingId}")
    public TrainingDto publishTraining(@PathVariable("trainingId") Long trainingId) {
        return trainingService.publishTraining(trainingId);
    }

    @PutMapping("/is-done/{trainingId}")
    public TrainingDto markTrainingAsDone(@PathVariable("trainingId") Long trainingId) {
        return trainingService.markTrainingAsDone(trainingId);
    }
}
