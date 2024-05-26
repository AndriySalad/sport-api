package com.v1.sport.services;

import com.v1.sport.data.dto.UserProfileDto;

public interface UserProfileService {
    UserProfileDto getUserProfile();

    UserProfileDto getUserProfileById(Long id);

    UserProfileDto updateUserProfile(UserProfileDto dto);
}
