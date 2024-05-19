package com.v1.sport.data.dto;

import com.v1.sport.data.models.TrainAdvice;
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

    private Set<TrainAdvice> advices;

    private boolean isDone;
}
