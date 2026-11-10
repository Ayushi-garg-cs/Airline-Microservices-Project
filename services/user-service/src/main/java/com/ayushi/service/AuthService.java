package com.ayushi.service;

import com.ayushi.payload.dto.UserDTO;
import com.ayushi.payload.response.AuthResponse;

public interface AuthService {
    AuthResponse login(String email, String password);
    AuthResponse signup(UserDTO userDto);
}
