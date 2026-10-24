package com.example.studentapi.service;

import com.example.studentapi.dto.LoginRequest;
import com.example.studentapi.dto.LoginResponse;
import com.example.studentapi.entity.User;
import com.example.studentapi.exception.InvalidCredentialsException;
import com.example.studentapi.repository.UserRepository;
import com.example.studentapi.security.JwtUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthServiceimplTest {

    @Mock
//    @Mock: Mockito tạo một StudentRepository giả. Nó không nối MySQL, ban đầu trả null hoặc rỗng cho mọi lời gọi.
    private UserRepository userRepository;
    //@InjectMocks: Mockito tạo StudentServiceImpl thật, rồi nhét cái repository giả vào field @Autowired private StudentRepository studentRepository bên trong nó
    @Mock
    private AuthServiceImpl authService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private AuthServiceImpl AuthService;

    @Test
    void login_khidungpassword_travetoken(){
        User user = new User("admin", "hashDaMaHoa", "ADMIN");
        when(userRepository.findByUsername("admin")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("123456", "hashDaMaHoa")).thenReturn(true);
        when(jwtUtil.generateToken("admin","ADMIN")).thenReturn("tokenfake");
        LoginRequest request = new LoginRequest();
        request.setUsername("admin");
        request.setPassword("123456");

        LoginResponse result = authService.login(request);

        assertEquals("token-gia", result.getToken());
        assertEquals("ADMIN", result.getRole());


    }
    @Test
    void login_khiSaiPassword_nemInvalidCredentialsException() {

        User user = new User("admin", "hashDaMaHoa", "ADMIN");
        when(userRepository.findByUsername("admin")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("sai", "hashDaMaHoa")).thenReturn(false);

        LoginRequest request = new LoginRequest();
        request.setUsername("admin");
        request.setPassword("sai");

        assertThrows(InvalidCredentialsException.class, () -> authService.login(request));
    }






}
