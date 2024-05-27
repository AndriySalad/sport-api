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
    public GenericMessage createRequest(@RequestParam("athleteId") Long athleteId, @RequestParam("trainerId") Long trainerId, @RequestParam("type") String type) {
        athleteTrainerService.createRequest(athleteId, trainerId, type);
        return new GenericMessage("Request sent from athlete with id " + athleteId + " to trainer with id " + trainerId);
    }

    @PostMapping("/handle-request")
    public GenericMessage handleRequest(@RequestParam Long athleteId, @RequestParam Long trainerId, @RequestParam String action) {
        athleteTrainerService.handleRequest(athleteId, trainerId, action);
        return new GenericMessage("Request handled with action: " + action);
    }

    @GetMapping("/athlete/{athleteId}")
    public TraineeDto getAthlete(@PathVariable("athleteId") Long athleteId){
        return athleteTrainerService.getAthlete(athleteId);
    }

    @GetMapping("{trainerId}/athletes")
    public List<TraineeDto> getTraineesByTrainer(@PathVariable("trainerId") Long trainerId){
        return athleteTrainerService.getAthletesByTrainer(trainerId);
    }

    @GetMapping("/trainer/{trainerId}")
    public TrainerDto getTrainer(@PathVariable("trainerId") Long trainerId){
        return athleteTrainerService.getTrainer(trainerId);
    }

    @GetMapping("/athletes")
    public List<UserListItemDto> getAllTrainees(){
        return athleteTrainerService.getAllTrainees();
    }

    @GetMapping("/trainers")
    public List<UserListItemDto> getAllTrainers(){
        return athleteTrainerService.getAllTrainers();
    }
}
