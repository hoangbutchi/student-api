package com.example.studentapi.exception;

public class InvalidCredentialsException extends RuntimeException {
    public InvalidCredentialsException() {
        super("Sai tên đăng nhập hoặc mật khẩu");
    }
}