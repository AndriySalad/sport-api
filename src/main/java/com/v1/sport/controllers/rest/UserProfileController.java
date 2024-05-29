package com.v1.sport.controllers.rest;

import com.v1.sport.data.dto.TrainingDto;
import com.v1.sport.data.dto.UserProfileDto;
import com.v1.sport.services.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user-profile")
public class UserProfileController {

    private final UserProfileService userProfileService;

    @GetMapping
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_TRAINER')")
    public UserProfileDto getMyProfile() {
        return userProfileService.getUserProfile();
    }

    @PutMapping
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_TRAINER')")
    public UserProfileDto updateMyProfile(@RequestBody UserProfileDto dto) {
        return userProfileService.updateUserProfile(dto);
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_TRAINER')")
    public UserProfileDto getUserProfileById(@PathVariable("id") Long id) {
        return userProfileService.getUserProfileById(id);
    }

    @GetMapping("/between/{startDate}/and/{endDate}")
    public List<TrainingDto> getTrainingsByAthlete(@PathVariable("startDate") String startDate,
                                                   @PathVariable("endDate") String endDate) {
        return userProfileService.getMyTrainings(startDate, endDate);
    }

}
