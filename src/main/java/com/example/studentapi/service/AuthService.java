package com.example.studentapi.service;

import com.example.studentapi.dto.LoginRequest;
import com.example.studentapi.dto.LoginResponse;

public interface AuthService {
    LoginResponse login(LoginRequest request);
    void register(LoginRequest request);
}