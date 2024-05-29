package com.v1.sport.data.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StravaTokenDto {
    private String access_token;
    private String refresh_token;
    private long expires_at;
    private long expires_in;
    private StravaAthlete athlete;
}
