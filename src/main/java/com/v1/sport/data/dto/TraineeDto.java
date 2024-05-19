package com.v1.sport.data.dto;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TraineeDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String userName;
    private String email;
    private String phone;
    private List<SocialMediaLinkDto> socialMediaLinks;
    private List<DescriptionItemDto> descriptions;
    private List<TrainingDto> trainers;
}
