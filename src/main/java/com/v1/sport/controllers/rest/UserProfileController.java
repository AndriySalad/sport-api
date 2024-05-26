package com.v1.sport.controllers.rest;

import com.v1.sport.data.dto.UserProfileDto;
import com.v1.sport.services.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_TRAINER')")
    public UserProfileDto getUserProfileById(@PathVariable("id") Long id) {
        return userProfileService.getUserProfileById(id);
    }

}
