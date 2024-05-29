package com.v1.sport.services;

import com.v1.sport.data.dto.StravaTokenDto;
import com.v1.sport.data.models.User;

public interface StravaService {
    StravaTokenDto exchangeCodeForToken(String userEmail, String code);
    void saveOrUpdateToken(User user, StravaTokenDto tokenResponse);
}
