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

    @PostMapping("/request")
    public GenericMessage createRequest(@RequestParam Long athleteId, @RequestParam Long trainerId, @RequestParam String type) {
        athleteTrainerService.createRequest(athleteId, trainerId, type);
        return new GenericMessage("Request sent from athlete with id " + athleteId + " to trainer with id " + trainerId);
    }

    @PostMapping("/handle-request")
    public GenericMessage handleRequest(@RequestParam Long athleteId, @RequestParam Long trainerId, @RequestParam String action) {
        athleteTrainerService.handleRequest(athleteId, trainerId, action);
        return new GenericMessage("Request handled with action: " + action);
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
