package com.example.studentapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class TeacherRequest {

    @NotBlank(message = "Tên không được để trống")
    private String name;

    @NotNull(message = "Môn không được để trống")
    private String subject;

    public TeacherRequest() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}