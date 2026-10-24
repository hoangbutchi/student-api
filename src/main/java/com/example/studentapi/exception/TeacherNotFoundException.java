package com.example.studentapi.exception;

public class TeacherNotFoundException extends RuntimeException {
    public TeacherNotFoundException(Integer id) {
        super("Không tìm thấy giáo viên có id = " + id);
    }

}