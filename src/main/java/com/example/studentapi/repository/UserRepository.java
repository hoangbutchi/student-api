package com.example.studentapi.repository;

import com.example.studentapi.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {
    Optional<User> findByUsername(String username);
//    KHÔNG BAO GIỜ trùng — đã khai báo unique = true ,chỉ tra về 1 giá trị
//    Chỉ có thể là 0 hoặc 1
//    Optional<User> là 1 "cái hộp" có thể chứa 1 User bên trong, hoặc rỗng — nhưng bản thân cái hộp đó không bao giờ là null. Việc này bắt buộc người gọi phải tự quyết định xử lý khi rỗng, thay vì vô tình quên:
}