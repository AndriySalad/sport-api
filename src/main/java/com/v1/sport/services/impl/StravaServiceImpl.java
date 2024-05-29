package com.v1.sport.services.impl;

import com.v1.sport.data.dto.StravaTokenDto;
import com.v1.sport.data.models.StravaToken;
import com.v1.sport.data.models.User;
import com.v1.sport.repository.StravaTokenRepository;
import com.v1.sport.repository.UserRepository;
import com.v1.sport.services.StravaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Service
@RequiredArgsConstructor
public class StravaServiceImpl implements StravaService {

    @Value("${strava.client_id}")
    private String clientId;

    @Value("${strava.client_secret}")
    private String clientSecret;

    private final UserRepository userRepository;
    private final StravaTokenRepository stravaTokenRepository;

    @Override
    public StravaTokenDto exchangeCodeForToken(String userEmail, String code) {
        User user = userRepository.findByEmail(userEmail).orElseThrow(() -> new RuntimeException("User not found"));

        String tokenUrl = "https://www.strava.com/oauth/token";
        RestTemplate restTemplate = new RestTemplate();

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("client_id", clientId);
        params.add("client_secret", clientSecret);
        params.add("code", code);
        params.add("grant_type", "authorization_code");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        ResponseEntity<StravaTokenDto> response = restTemplate.postForEntity(tokenUrl, request, StravaTokenDto.class);

        if (response.getStatusCode() == HttpStatus.OK) {
            StravaTokenDto tokenResponse = response.getBody();
            saveOrUpdateToken(user, tokenResponse);
            return tokenResponse;
        } else {
            throw new RuntimeException("Error exchanging code for token: " + response.getStatusCode());
        }
    }

    @Override
    public void saveOrUpdateToken(User user, StravaTokenDto tokenResponse) {
        StravaToken token = stravaTokenRepository.findByUser(user).orElse(new StravaToken());
        token.setUser(user);
        token.setStravaUserId(tokenResponse.getAthlete().getId());
        token.setAccessToken(tokenResponse.getAccess_token());
        token.setRefreshToken(tokenResponse.getRefresh_token());
        token.setExpiresAt(tokenResponse.getExpires_at());
        stravaTokenRepository.save(token);
    }
}
