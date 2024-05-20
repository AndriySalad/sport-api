package com.v1.sport.data.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TrainingAdviceDto {
    private Long id;

    private String title;

    private String description;
}
