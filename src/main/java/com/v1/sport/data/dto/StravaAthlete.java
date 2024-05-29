package com.v1.sport.data.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StravaAthlete {
    private Long id;
    private String username;
    private String firstname;
    private String lastname;
}
