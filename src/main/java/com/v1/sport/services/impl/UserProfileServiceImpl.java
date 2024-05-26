package com.v1.sport.services.impl;

import com.v1.sport.Exceptions.EntityNotFoundException;
import com.v1.sport.data.dto.SocialMediaLinkDto;
import com.v1.sport.data.dto.UserProfileDto;
import com.v1.sport.data.models.SocialMediaLink;
import com.v1.sport.data.models.User;
import com.v1.sport.repository.UserRepository;
import com.v1.sport.services.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserProfileServiceImpl implements UserProfileService {

    private final UserRepository userRepository;

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
                .build();

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
                .build();

        return userProfileDto;
    }
}