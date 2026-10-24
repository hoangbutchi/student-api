package com.example.studentapi.service;

import com.example.studentapi.dto.LoginRequest;
import com.example.studentapi.dto.LoginResponse;
import com.example.studentapi.entity.User;
import com.example.studentapi.exception.InvalidCredentialsException;
import com.example.studentapi.exception.UsernameAlreadyExistsException;
import com.example.studentapi.repository.UserRepository;
import com.example.studentapi.security.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtil jwtUtil;

    //Bước 1: POST /auth/login (username, password đúng)
//        ↓
//   AuthServiceImpl tạo token bằng jwtUtil.generateToken(username)
//        ↓
//   Trả về: { "token": "eyJhbGci..." }
//
//Bước 2: Client lưu token lại
//
//Bước 3: GET /students
//        Header: Authorization: Bearer eyJhbGci...
//        ↓
//   JwtAuthFilter chạy TRƯỚC:
//      - Đọc header
//      - jwtUtil.isTokenValid(token) → true
//      - jwtUtil.extractUsername(token) → "admin"
//      - SecurityContextHolder.setAuthentication(...) → đánh dấu "đã đăng nhập"
//        ↓
//   SecurityFilterChain kiểm tra .anyRequest().authenticated()
//      → ĐÃ được đánh dấu authenticated ở bước trên → CHO QUA
//        ↓
//   StudentController.getAll() chạy bình thường
//        ↓
//   Trả kết quả về client
    @Override
    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(InvalidCredentialsException::new);
//        Chạy ra khỏi AuthServiceImpl.login() (nơi throw xảy ra)
//Chạy tiếp ra khỏi AuthController.login() (vì Controller không bắt)
//Spring (tầng điều phối trung tâm) nhận diện và chuyển nó tới GlobalExceptionHandler
//        Spring tìm: "Trong GlobalExceptionHandler (đánh dấu @RestControllerAdvice),
//             có method nào @ExceptionHandler(InvalidCredentialsException.class)

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException();
        }

        String token = jwtUtil.generateToken(user.getUsername(), user.getRole());

        return new LoginResponse(user.getId(), user.getUsername(), user.getRole(), token);
    }

    @Override
    public void register(LoginRequest request) {
        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new UsernameAlreadyExistsException(request.getUsername());
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole("USER");
        userRepository.save(user);
    }
}