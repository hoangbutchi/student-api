package com.example.studentapi.repository;

import com.example.studentapi.entity.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface teacherRepository extends JpaRepository<Teacher, Integer> {
    // Tìm chính xác theo tên
    List<Teacher> findByName(String name);
//    CÓ THỂ trùng — nhiều sinh viên có thể tên "An"

    // Tìm gần đúng (chứa từ khóa), không phân biệt hoa thường
    List<Teacher> findByNameContainingIgnoreCase(String keyword);

}