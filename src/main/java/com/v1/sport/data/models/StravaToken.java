package com.v1.sport.data.models;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "strava_tokens")
@Table
public class StravaToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "strava_user_id")
    private Long stravaUserId;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name = "access_token")
    private String accessToken;

    @Column(name = "refresh_token")
    private String refreshToken;

    @Column(name = "expires_at")
    private Long expiresAt;
}
