package com.v1.sport.data.dto;

import com.v1.sport.data.models.ExerciseType;
import com.v1.sport.data.models.Training;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ExerciseDto {
    private Long id;

    private String title;

    private String description;

    private String type;

    private String measurement;

    private Long sets;

    private Long repetitions;

    private Boolean completed;

}
