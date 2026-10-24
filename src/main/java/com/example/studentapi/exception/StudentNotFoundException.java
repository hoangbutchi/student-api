package com.example.studentapi.exception;

public class StudentNotFoundException extends RuntimeException {
    public StudentNotFoundException(Integer id) {
        super("Không tìm thấy sinh viên có id = " + id);
    }

}
