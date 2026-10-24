package com.example.studentapi.service;

import com.example.studentapi.dto.PageResponse;
import com.example.studentapi.dto.StudentRequest;
import com.example.studentapi.dto.StudentResponse;
import com.example.studentapi.entity.Student;
import com.example.studentapi.exception.StudentNotFoundException;
import com.example.studentapi.repository.StudentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    private StudentRepository studentRepository;

    // Convert Entity -> Response DTO
    private StudentResponse toResponse(Student student) {
        return new StudentResponse(student.getId(), student.getName(), student.getAge());
    }
//    dc them private tùy í,nhưng public phải theo studentservice

    @Override
    public List<StudentResponse> getAll() {
        return studentRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
    @Override
    public StudentResponse getById(Integer id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(id));
        return toResponse(student);
    }

    @Override
    public StudentResponse create(StudentRequest request) {
        Student student = new Student();
        student.setName(request.getName());
        student.setAge(request.getAge());
        Student saved = studentRepository.save(student);
        return toResponse(saved);
    }

    @Override
    public StudentResponse update(Integer id, StudentRequest request) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new StudentNotFoundException(id));

        student.setName(request.getName());
        student.setAge(request.getAge());
        Student updated = studentRepository.save(student);
        return toResponse(updated);
    }

    @Override
    public void delete(Integer id) {
        if (!studentRepository.existsById(id)) {
            throw new StudentNotFoundException(id);
        }
        studentRepository.deleteById(id);
    }

    @Override
    public List<StudentResponse> searchByName(String name) {
        return studentRepository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<StudentResponse> getByAgeGreaterThan(Integer age) {
        return studentRepository.findByAgeGreaterThan(age)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<StudentResponse> getByAgeRange(Integer min, Integer max) {
        return studentRepository.findByAgeBetween(min, max)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
    @Override
    public PageResponse<StudentResponse> getAllPaged(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());

//                Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending()); ham  nay tu co khi import
        Page<Student> studentPage = studentRepository.findAll(pageable);
//        page co mang gia tri stuendt(id,name,age) va ca gia tri  findAll(pageable)
//        <> la wrapper chua nhieu thu
//        Giả sử database có 12 sinh viên, bạn gọi với pageable = (page=0, size=5, sort theo name):
//
//
//studentPage = {
//    content         = [Student1, Student2, Student3, Student4, Student5],  // 5 sinh viên của trang 0
//    number          = 0,          // trang hiện tại
//    size            = 5,          // kích thước mỗi trang
//    totalElements   = 12,         // tổng số sinh viên trong TOÀN BỘ database
//    totalPages      = 3,          // tổng số trang (ceil(12/5) = 3)
//    first           = true,       // có phải trang đầu tiên không
//    last            = false,      // có phải trang cuối cùng không
//    numberOfElements = 5          // số phần tử THẬT SỰ có trong trang này
//}
//        -- Câu 1: lấy đúng 5 dòng của trang 0 (dựa vào thông tin trong pageable)
//SELECT * FROM students ORDER BY name ASC LIMIT 5 OFFSET 0;
//
//-- Câu 2: đếm tổng số dòng trong bảng
//SELECT COUNT(*) FROM students;

        List<StudentResponse> data = studentPage.getContent()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());

        return new PageResponse<>(
                data,
                studentPage.getNumber(),
                studentPage.getSize(),
                studentPage.getTotalElements(),
                studentPage.getTotalPages()
        );
    }

}