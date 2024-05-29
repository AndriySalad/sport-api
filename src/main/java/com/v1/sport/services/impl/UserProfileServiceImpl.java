package com.v1.sport.services.impl;

import com.v1.sport.Exceptions.EntityNotFoundException;
import com.v1.sport.data.dto.*;
import com.v1.sport.data.models.*;
import com.v1.sport.repository.NotificationRepository;
import com.v1.sport.repository.StravaTokenRepository;
import com.v1.sport.repository.TrainingRepository;
import com.v1.sport.repository.UserRepository;
import com.v1.sport.services.StravaService;
import com.v1.sport.services.TrainingService;
import com.v1.sport.services.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserProfileServiceImpl implements UserProfileService {

    private final UserRepository userRepository;
    private final TrainingRepository trainingRepository;
    private final TrainingService trainingService;
    private final NotificationRepository notificationRepository;
    private final StravaTokenRepository stravaTokenRepository;
    private final StravaService stravaService;

    @Override
    public UserProfileDto getUserProfile() {
        User user = userRepository
                .findByEmail(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        List<SocialMediaLink> socialMediaLinks = new ArrayList<>(user.getSocialMediaLinks());
        List<SocialMediaLinkDto> socialMediaLinkDtos = socialMediaLinks.stream()
                .map(socialMediaLink -> SocialMediaLinkDto.builder()
                        .id(socialMediaLink.getId())
                        .link(socialMediaLink.getLink())
                        .title(socialMediaLink.getTitle())
                        .build())
                .toList();

        UserProfileDto userProfileDto = UserProfileDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phone(user.getPhoneNumber())
                .userName(user.getName())
                .socialMediaLinks(socialMediaLinkDtos)
                .injuryDescription(user.getInjuryDescription())
                .experienceDescription(user.getExperienceDescription())
                .goalDescription(user.getGoalDescription())
                .role(user.getRole().toString())
                .build();

        Long notificationCount = notificationRepository.countByReceiverAndIsViewed(user, false);
        userProfileDto.setNotificationCount(notificationCount);
        if (user.getRole().equals(Role.ROLE_TRAINER)) {
            userProfileDto.setAthletes(user.getAthletes().stream()
                    .map(athlete -> UserListItemDto.builder()
                            .id(athlete.getId())
                            .userName(athlete.getName())
                            .email(athlete.getEmail())
                            .build())
                    .toList());
        } else if (user.getRole().equals(Role.ROLE_USER)) {
            userProfileDto.setTrainerId(user.getTrainer() != null ? user.getTrainer().getId() : null);
        }

        Optional<StravaToken> stravaToken = stravaTokenRepository.findByUser(user);
        if (stravaToken.isPresent()) {
            StravaToken stravaToken1 = stravaToken.get();
            StravaRunStatsDto stravaRunStats = stravaService.getRunStats(stravaToken1.getAccessToken(), stravaToken1.getStravaUserId());
            userProfileDto.setStravaRunStats(stravaRunStats);
        }


        return userProfileDto;
    }

    @Override
    public UserProfileDto getUserProfileById(Long id) {
        User user = userRepository
                .findById(id).orElseThrow(() -> new EntityNotFoundException("User not found"));

        List<SocialMediaLink> socialMediaLinks = new ArrayList<>(user.getSocialMediaLinks());
        List<SocialMediaLinkDto> socialMediaLinkDtos = socialMediaLinks.stream()
                .map(socialMediaLink -> SocialMediaLinkDto.builder()
                        .id(socialMediaLink.getId())
                        .link(socialMediaLink.getLink())
                        .title(socialMediaLink.getTitle())
                        .build())
                .toList();

        UserProfileDto userProfileDto = UserProfileDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phone(user.getPhoneNumber())
                .userName(user.getName())
                .socialMediaLinks(socialMediaLinkDtos)
                .injuryDescription(user.getInjuryDescription())
                .experienceDescription(user.getExperienceDescription())
                .goalDescription(user.getGoalDescription())
                .build();

        return userProfileDto;
    }

    @Override
    public UserProfileDto updateUserProfile(UserProfileDto dto) {
        User user = userRepository
                .findByEmail(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        user.setEmail(dto.getEmail());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setPhoneNumber(dto.getPhone());
        user.setName(dto.getUserName());
        user.setGoalDescription(dto.getGoalDescription());
        user.setExperienceDescription(dto.getExperienceDescription());
        user.setInjuryDescription(dto.getInjuryDescription());

        User updatedUser =  userRepository.save(user);
        List<SocialMediaLinkDto> socialMediaLinkDtos = updatedUser.getSocialMediaLinks().stream()
                .map(socialMediaLink -> SocialMediaLinkDto.builder()
                        .id(socialMediaLink.getId())
                        .link(socialMediaLink.getLink())
                        .title(socialMediaLink.getTitle())
                        .build())
                .toList();

        UserProfileDto userProfileDto = UserProfileDto.builder()
                .id(user.getId())
                .email(user.getEmail())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .phone(user.getPhoneNumber())
                .userName(user.getName())
                .socialMediaLinks(socialMediaLinkDtos)
                .injuryDescription(user.getInjuryDescription())
                .experienceDescription(user.getExperienceDescription())
                .goalDescription(user.getGoalDescription())
                .build();
        return userProfileDto;
    }

    @Override
    public List<TrainingDto> getMyTrainings(String startDate, String endDate) {
        User user = userRepository
                .findByEmail(SecurityContextHolder.getContext().getAuthentication().getName())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        List<Training> trainings = trainingRepository
                .findAllByUserIdAndDateBetween(user.getId(), startDate, endDate);
        return trainings.stream()
                .map(trainingService::mapToDto)
                .toList();
    }
}
