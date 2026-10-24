package com.example.studentapi.dto;

public class LoginResponse {
    private Integer id;
    private String username;
    private String role;
    private String token;   // mới

    public LoginResponse() {}

    public LoginResponse(Integer id, String username, String role,String token) {
        this.id = id;
        this.username = username;
        this.role = role;
        this.token = token;
    }



    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getRole() { return role; }
    public void setRole(String role) { this.role = role; }
}