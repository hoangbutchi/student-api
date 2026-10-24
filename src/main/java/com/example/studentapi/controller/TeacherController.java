package com.example.studentapi.controller;

import com.example.studentapi.dto.ApiResponse;
import com.example.studentapi.dto.PageResponse;
import com.example.studentapi.dto.TeacherRequest;
import com.example.studentapi.dto.TeacherResponse;
import com.example.studentapi.service.TeacherService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/teachers")
public class TeacherController {

    @Autowired
    private TeacherService TeacherService;

//    get /Teachers
    @GetMapping
    public ApiResponse<List<TeacherResponse>> getAll() {
        List<TeacherResponse> Teachers = TeacherService.getAll();
        return ApiResponse.success("Lấy danh sách sinh viên thành công", Teachers);
    }

    @GetMapping("/{id}")
    public ApiResponse<TeacherResponse> getById(@PathVariable Integer id) {
        TeacherResponse Teacher = TeacherService.getById(id);
        return ApiResponse.success("Lấy thông tin sinh viên thành công", Teacher);
    }

//post /Teachers
//    cac @valid,@.. la cac annotation
//    valid chan class Teacherrequest cac notnull,@min
    @PostMapping
    public ApiResponse<TeacherResponse> create(@Valid @RequestBody TeacherRequest request) {
        TeacherResponse created = TeacherService.create(request);
        return ApiResponse.success("Tạo sinh viên thành công", created);
    }

    @PutMapping("/{id}")
    public ApiResponse<TeacherResponse> update(@PathVariable Integer id, @Valid @RequestBody TeacherRequest request) {
        TeacherResponse updated = TeacherService.update(id, request);
        return ApiResponse.success("Cập nhật sinh viên thành công", updated);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Integer id) {
        TeacherService.delete(id);
        return ApiResponse.success("Đã xóa sinh viên có id = " + id, null);
    }

    @GetMapping("/search")
    public ApiResponse<List<TeacherResponse>> searchByName(@RequestParam String name) {
        List<TeacherResponse> result = TeacherService.searchByName(name);
        return ApiResponse.success("Tìm kiếm thành công", result);
    }

    
    @GetMapping("/paged")
    public ApiResponse<PageResponse<TeacherResponse>> getAllPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy) {
        PageResponse<TeacherResponse> result = TeacherService.getAllPaged(page, size, sortBy);
        return ApiResponse.success("Lấy danh sách phân trang thành công", result);
    }
}