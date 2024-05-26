package com.v1.sport.data.dto;

import lombok.*;

import java.util.Set;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TrainingDto {
    private Long id;

    private String title;

    private String description;

    private String date;

    private Set<ExerciseDto> exercises;

    private Long creatorId;
}
