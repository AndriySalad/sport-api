package com.v1.sport.data.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserProfileDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String userName;
    private String email;
    private String phone;
    private String goalDescription;
    private String experienceDescription;
    private String injuryDescription;
    private String role;
    private List<SocialMediaLinkDto> socialMediaLinks;
    private List<UserListItemDto> athletes;
    private Long trainerId;
    private Long notificationCount;
    private StravaRunStatsDto stravaRunStats;
}
