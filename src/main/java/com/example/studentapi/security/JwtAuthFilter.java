package com.example.studentapi.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {
//    OncePerRequestFilter chay dau tien

    @Autowired
    private JwtUtil jwtUtil;
//    luon luon chay dau tien de check token

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
//        HttpServletRequest có sẵn hàm getHeader(String headerName) — hàm này đọc toàn bộ danh sách header mà client đã gửi kèm trong request, rồi lấy ra đúng giá trị của header có tên "Authorization".

        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            String token = authHeader.substring(7); // bỏ chữ "Bearer " (7 ký tự)


            if (jwtUtil.isTokenValid(token)) {
                String username = jwtUtil.extractUsername(token);
                String role = jwtUtil.extractRole(token);
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                username,
                                null,
                                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role))
                        );

                SecurityContextHolder.getContext().setAuthentication(authToken);
//                request này đã được xác thực, đây là user username". Từ giờ, Spring Security sẽ coi request này là đã đăng nhập (authenticated()), cho phép đi qua bước kiểm tra ở SecurityFilterChain
            }
        }

        filterChain.doFilter(request, response); // cho request đi tiếp
//        filterChain.doFilter(request, response) — dòng bắt buộc phải có ở cuối, nghĩa là "cho request tiếp tục đi tới bước xử lý kế tiếp" (tới Controller nếu hợp lệ, hoặc tới bước chặn 403 nếu Filter này không đánh dấu authenticated).
    }
}