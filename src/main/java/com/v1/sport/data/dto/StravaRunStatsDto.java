package com.v1.sport.data.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StravaRunStatsDto {
    private Long totalRunDistance;
    private Long totalRunTime;
    private Long totalRuns;
    private Long maxRunDistance;
}

