package com.example.studentapi.service;

import com.example.studentapi.dto.PageResponse;
import com.example.studentapi.dto.TeacherRequest;
import com.example.studentapi.dto.TeacherResponse;

import java.util.List;

public interface TeacherService {
    List<TeacherResponse> getAll();
    TeacherResponse getById(Integer id);
    TeacherResponse create(TeacherRequest request);
    TeacherResponse update(Integer id, TeacherRequest request);
    void delete(Integer id);
    List<TeacherResponse> searchByName(String name);
    PageResponse<TeacherResponse> getAllPaged(int page, int size, String sortBy);

}