package com.v1.sport.data.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StravaAthleteStatsDto {
    private ActivityTotals recentRunTotals;
    private ActivityTotals ytdRunTotals;
    private ActivityTotals allRunTotals;

    @Getter
    @Setter
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ActivityTotals {
        private Long count;        // Кількість активностей
        private Long distance;     // Загальна дистанція
        private Long movingTime;   // Загальний час у русі
        private Long elevationGain; // Загальне підняття
    }
}
