# Student Management REST API

REST API quản lý sinh viên và giáo viên, có đăng nhập bằng JWT và phân quyền theo role.

## Công nghệ

- Java 17, Spring Boot
- Spring Data JPA, MySQL
- Spring Security, JWT
- Swagger UI (springdoc-openapi)
- JUnit 5, Mockito

## Tính năng

- CRUD sinh viên, giáo viên
- Tìm kiếm, phân trang, sắp xếp
- Validate dữ liệu, xử lý lỗi tập trung (HTTP 400, 401, 404, 409)
- Đăng ký, đăng nhập, xác thực bằng JWT
- Phân quyền: chỉ ADMIN được xóa sinh viên
- Unit test cho tầng Service

## Kiến trúc

Controller → Service → Repository → MySQL
(DTO tách riêng với Entity)

## Cách chạy

1. Tạo database:
   ```sql
   CREATE DATABASE student_api_db;
   ```
2. Đặt biến môi trường:
    - `DB_PASSWORD`: mật khẩu MySQL
    - `JWT_SECRET`: chuỗi bí mật, tối thiểu 32 ký tự
3. Chạy `StudentApiApplication`.
4. Mở Swagger: http://localhost:8080/swagger-ui.html

## API chính

| Method | URL | Mô tả | Cần token |
|---|---|---|---|
| POST | /auth/register | Đăng ký | Không |
| POST | /auth/login | Đăng nhập, nhận token | Không |
| GET | /students | Danh sách sinh viên | Có |
| GET | /students/paged | Phân trang | Có |
| POST | /students | Thêm sinh viên | Có |
| PUT | /students/{id} | Cập nhật | Có |
| DELETE | /students/{id} | Xóa (ADMIN) | Có |

## Tác giả

hoang