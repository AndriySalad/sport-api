package com.v1.sport.data.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SocialMediaLinkDto {
    private Long id;

    private String link;

    private String title;
}
