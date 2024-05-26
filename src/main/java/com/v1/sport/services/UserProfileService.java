package com.v1.sport.services;

import com.v1.sport.data.dto.TrainingDto;
import com.v1.sport.data.dto.UserProfileDto;

import java.util.List;

public interface UserProfileService {
    UserProfileDto getUserProfile();

    UserProfileDto getUserProfileById(Long id);

    UserProfileDto updateUserProfile(UserProfileDto dto);

    List<TrainingDto> getMyTrainings(String startDate, String endDate);
}
