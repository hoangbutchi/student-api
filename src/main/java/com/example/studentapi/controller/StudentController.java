package com.example.studentapi.controller;

import com.example.studentapi.dto.ApiResponse;
import com.example.studentapi.dto.PageResponse;
import com.example.studentapi.dto.StudentRequest;
import com.example.studentapi.dto.StudentResponse;
import com.example.studentapi.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {

    @Autowired
    private StudentService studentService;

//    get /students
    @GetMapping
    public ApiResponse<List<StudentResponse>> getAll() {
        List<StudentResponse> students = studentService.getAll();
        return ApiResponse.success("Lấy danh sách sinh viên thành công", students);
    }

    @GetMapping("/{id}")
    public ApiResponse<StudentResponse> getById(@PathVariable Integer id) {
        StudentResponse student = studentService.getById(id);
        return ApiResponse.success("Lấy thông tin sinh viên thành công", student);
    }

//post /students
//    cac @valid,@.. la cac annotation
//    valid chan class studentrequest cac notnull,@min
    @PostMapping
    public ApiResponse<StudentResponse> create(@Valid @RequestBody StudentRequest request) {
        StudentResponse created = studentService.create(request);
        return ApiResponse.success("Tạo sinh viên thành công", created);
    }

    @PutMapping("/{id}")
    public ApiResponse<StudentResponse> update(@PathVariable Integer id, @Valid @RequestBody StudentRequest request) {
        StudentResponse updated = studentService.update(id, request);
        return ApiResponse.success("Cập nhật sinh viên thành công", updated);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Integer id) {
        studentService.delete(id);
        return ApiResponse.success("Đã xóa sinh viên có id = " + id, null);
    }

    @GetMapping("/search")
    public ApiResponse<List<StudentResponse>> searchByName(@RequestParam String name) {
        List<StudentResponse> result = studentService.searchByName(name);
        return ApiResponse.success("Tìm kiếm thành công", result);
    }

    @GetMapping("/age-greater-than")
    public ApiResponse<List<StudentResponse>> getByAgeGreaterThan(@RequestParam Integer age) {
        List<StudentResponse> result = studentService.getByAgeGreaterThan(age);
        return ApiResponse.success("Lấy danh sách thành công", result);
    }

    @GetMapping("/age-range")
    public ApiResponse<List<StudentResponse>> getByAgeRange(@RequestParam Integer min, @RequestParam Integer max) {
        List<StudentResponse> result = studentService.getByAgeRange(min, max);
        return ApiResponse.success("Lấy danh sách thành công", result);
    }

    @GetMapping("/paged")
    public ApiResponse<PageResponse<StudentResponse>> getAllPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,
            @RequestParam(defaultValue = "id") String sortBy) {
        PageResponse<StudentResponse> result = studentService.getAllPaged(page, size, sortBy);
        return ApiResponse.success("Lấy danh sách phân trang thành công", result);
    }
}