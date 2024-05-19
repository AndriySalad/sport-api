package com.v1.sport.data.dto;

import com.v1.sport.data.models.DescriptionType;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DescriptionItemDto {

    private Long id;

    private String title;

    private String description;

    private String descriptionType;
}
