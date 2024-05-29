package com.v1.sport.controllers.rest;

import com.v1.sport.data.dto.StravaTokenDto;
import com.v1.sport.services.StravaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/strava")
public class StravaController {

    private final StravaService stravaService;

    @GetMapping("/callback")
    public ResponseEntity<?> stravaCallback(@RequestParam String code) {
        try {
            String userEmail = SecurityContextHolder.getContext().getAuthentication().getName();
            StravaTokenDto tokenResponse = stravaService.exchangeCodeForToken(userEmail, code);
            return ResponseEntity.ok(tokenResponse);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Error exchanging code for token: " + e.getMessage());
        }
    }
}

