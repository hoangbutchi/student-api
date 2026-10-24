package com.example.studentapi.service;

import com.example.studentapi.dto.PageResponse;
import com.example.studentapi.dto.StudentRequest;
import com.example.studentapi.dto.StudentResponse;

import java.util.List;

public interface StudentService {
    List<StudentResponse> getAll();
    StudentResponse getById(Integer id);
    StudentResponse create(StudentRequest request);
    StudentResponse update(Integer id, StudentRequest request);
    void delete(Integer id);
    List<StudentResponse> searchByName(String name);
    List<StudentResponse> getByAgeGreaterThan(Integer age);
    List<StudentResponse> getByAgeRange(Integer min, Integer max);
    PageResponse<StudentResponse> getAllPaged(int page, int size, String sortBy);

}