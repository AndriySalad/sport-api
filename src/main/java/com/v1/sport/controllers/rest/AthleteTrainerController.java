package com.v1.sport.controllers.rest;

import com.v1.sport.data.dto.TraineeDto;
import com.v1.sport.data.dto.TrainerDto;
import com.v1.sport.data.dto.UserListItemDto;
import com.v1.sport.services.AthleteTrainerService;
import com.v1.sport.utils.GenericMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/athlete-trainer")
public class AthleteTrainerController {

    private final AthleteTrainerService athleteTrainerService;

    @PostMapping("/to-be-trainee/{athleteId}/by-trainer/{trainerId}")
    public GenericMessage toBeTrainee(@PathVariable("athleteId") Long athleteId, @PathVariable("trainerId") Long trainerId){
        athleteTrainerService.toBeTrainee(athleteId, trainerId);
        return new GenericMessage("Athlete with id " + athleteId + " is now a trainee of trainer with id " + trainerId);
    }

    @PostMapping("/to-be-trainer/{trainerId}/for-trainee/{athleteId}")
    public GenericMessage toBeTrainer(@PathVariable("athleteId") Long athleteId, @PathVariable("trainerId") Long trainerId){
        athleteTrainerService.toBeTrainer(athleteId, trainerId);
        return new GenericMessage("Athlete with id " + athleteId + " is now a trainee of trainer with id " + trainerId);
    }

    @PostMapping("/accept-trainee/{athleteId}/by-trainer/{trainerId}")
    public GenericMessage acceptTrainee(@PathVariable("athleteId") Long athleteId, @PathVariable("trainerId") Long trainerId){
        athleteTrainerService.acceptTrainee(athleteId, trainerId);
        return new GenericMessage("Athlete with id " + athleteId + " is now accepted as a trainee of trainer with id " + trainerId);
    }

    @PostMapping("/reject-trainee/{athleteId}/by-trainer/{trainerId}")
    public GenericMessage rejectTrainee(@PathVariable("athleteId") Long athleteId, @PathVariable("trainerId") Long trainerId){
        athleteTrainerService.rejectTrainee(athleteId, trainerId);
        return new GenericMessage("Athlete with id " + athleteId + " is now rejected as a trainee of trainer with id " + trainerId);
    }

    @PostMapping("/remove-trainee/{athleteId}/from-trainer/{trainerId}")
    public GenericMessage removeTrainee(@PathVariable("athleteId") Long athleteId, @PathVariable("trainerId") Long trainerId){
        athleteTrainerService.removeTrainee(athleteId, trainerId);
        return new GenericMessage("Athlete with id " + athleteId + " is now removed as a trainee of trainer with id " + trainerId);
    }

    @PostMapping("/remove-trainer/{trainerId}/from-athlete/{athleteId}")
    public GenericMessage removeTrainer(@PathVariable("athleteId") Long athleteId, @PathVariable("trainerId") Long trainerId){
        athleteTrainerService.removeTrainer(athleteId, trainerId);
        return new GenericMessage("Trainer with id " + trainerId + " is now removed as a trainer of athlete with id " + athleteId);
    }

    @PostMapping("/accept-trainer/{trainerId}/by-athlete/{athleteId}")
    public GenericMessage acceptTrainer(@PathVariable("athleteId") Long athleteId, @PathVariable("trainerId") Long trainerId){
        athleteTrainerService.acceptTrainer(athleteId, trainerId);
        return new GenericMessage("Trainer with id " + trainerId + " is now accepted as a trainer of athlete with id " + athleteId);
    }

    @PostMapping("/reject-trainer/{trainerId}/by-athlete/{athleteId}")
    public GenericMessage rejectTrainer(@PathVariable("athleteId") Long athleteId, @PathVariable("trainerId") Long trainerId){
        athleteTrainerService.rejectTrainer(athleteId, trainerId);
        return new GenericMessage("Trainer with id " + trainerId + " is now rejected as a trainer of athlete with id " + athleteId);
    }

    @GetMapping("/get-trainees-by-trainer/{trainerId}")
    public List<TraineeDto> getTraineesByTrainer(@PathVariable("trainerId") Long trainerId){
        return athleteTrainerService.getTraineesByTrainer(trainerId);
    }

    @GetMapping("/get-trainer-by-trainee/{athleteId}")
    public TrainerDto getTrainerByTrainee(@PathVariable("athleteId") Long athleteId){
        return athleteTrainerService.getTrainerByTrainee(athleteId);
    }

    @GetMapping("/get-all-trainees")
    public List<UserListItemDto> getAllTrainees(){
        return athleteTrainerService.getAllTrainees();
    }

    @GetMapping("/get-all-trainers")
    public List<UserListItemDto> getAllTrainers(){
        return athleteTrainerService.getAllTrainers();
    }

}
