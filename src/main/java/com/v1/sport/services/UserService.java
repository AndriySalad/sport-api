package com.v1.sport.services;

import com.v1.sport.data.dto.AuthenticationRequestDto;
import com.v1.sport.data.dto.AuthenticationResponseDto;
import com.v1.sport.data.dto.RegisterRequestDto;
import com.v1.sport.data.models.User;

import java.util.List;

public interface UserService {
    AuthenticationResponseDto register(RegisterRequestDto request);
    AuthenticationResponseDto authenticate(AuthenticationRequestDto request);
    void saveUserToken(User user, String jwtToken);
    void revokeAllUserTokens(User user);
    User getByEmail(String email);

    List<User> getAll();

    User findById(Long id);

}
