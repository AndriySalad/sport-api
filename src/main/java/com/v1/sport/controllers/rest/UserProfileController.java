package com.v1.sport.controllers.rest;

import com.v1.sport.data.dto.UserProfileDto;
import com.v1.sport.services.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user-profile")
public class UserProfileController {

    private final UserProfileService userProfileService;

    @GetMapping
    public UserProfileDto getMyProfile() {
        return userProfileService.getUserProfile();
    }

    @GetMapping("/{id}")
    public UserProfileDto getUserProfileById(@PathVariable("id") Long id) {
        return userProfileService.getUserProfileById(id);
    }

}
