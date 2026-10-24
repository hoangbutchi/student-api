package com.example.studentapi.controller;

import com.example.studentapi.dto.ApiResponse;
import com.example.studentapi.dto.LoginRequest;
import com.example.studentapi.dto.LoginResponse;
import com.example.studentapi.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
//    valid ngan cho LOginrequest MethodArgumentNotValidException
    public ApiResponse<LoginResponse> login(@Valid @RequestBody LoginRequest request) {
        LoginResponse response = authService.login(request);
        return ApiResponse.success("Đăng nhập thành công", response);
    }
    @PostMapping("/register")
    public ApiResponse<Void> register(@Valid @RequestBody LoginRequest request) {
        authService.register(request);
        return ApiResponse.success("Đăng ký thành công", null);
    }
}