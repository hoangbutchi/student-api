package com.example.studentapi.repository;

import com.example.studentapi.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository extends JpaRepository<Student, Integer> {
    // Tìm chính xác theo tên
    List<Student> findByName(String name);
//    CÓ THỂ trùng — nhiều sinh viên có thể tên "An"

    // Tìm gần đúng (chứa từ khóa), không phân biệt hoa thường
    List<Student> findByNameContainingIgnoreCase(String keyword);

    // Tìm theo tuổi lớn hơn
    List<Student> findByAgeGreaterThan(Integer age);

    // Tìm theo tuổi nhỏ hơn
    List<Student> findByAgeLessThan(Integer age);

    // Tìm theo khoảng tuổi
    List<Student> findByAgeBetween(Integer minAge, Integer maxAge);

    // Sắp xếp theo tên tăng dần
    List<Student> findByAgeGreaterThanOrderByNameAsc(Integer age);
}