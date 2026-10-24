package com.example.studentapi.service;

import com.example.studentapi.dto.StudentRequest;
import com.example.studentapi.dto.StudentResponse;
import com.example.studentapi.entity.Student;
import com.example.studentapi.exception.StudentNotFoundException;
import com.example.studentapi.repository.StudentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
//@ExtendWith(MockitoExtension.class) bật Mockito cho class test. Nếu thiếu dòng này, @Mock và @InjectMocks bên dưới sẽ không làm gì cả, biến sẽ là null và test báo NullPointerException
class StudentServiceImplTest {

    @Mock
//    @Mock: Mockito tạo một StudentRepository giả. Nó không nối MySQL, ban đầu trả null hoặc rỗng cho mọi lời gọi.
    private StudentRepository studentRepository;
//@InjectMocks: Mockito tạo StudentServiceImpl thật, rồi nhét cái repository giả vào field @Autowired private StudentRepository studentRepository bên trong nó
    @InjectMocks
    private StudentServiceImpl studentService;

    @Test
    void getById_khiTimThay_traVeStudentResponse() {
        Student student = new Student();
        student.setId(1);
        student.setName("An");
        student.setAge(20);
//        phai gan gia tri cho student vi  studentRepository null luc dau
        when(studentRepository.findById(1)).thenReturn(Optional.of(student));
//        findById cua  studentRepository mac dinh tra ve Optional<T>

        StudentResponse result = studentService.getById(1);

        assertEquals(1, result.getId());
        assertEquals("An", result.getName());
        assertEquals(20, result.getAge());
    }
//    toResponse gán sai field (id, name, age); Service không convert sang DTO


    @Test
    void getById_khiKhongTimThay_nemStudentNotFoundException() {
        when(studentRepository.findById(999)).thenReturn(Optional.empty());

        assertThrows(StudentNotFoundException.class, () -> studentService.getById(999));
//        assertThrows nhận lambda
//        ↓
//Tự chạy: studentService.getById(999)
//        ↓
//Bên trong: mock trả Optional.empty() → orElseThrow ném StudentNotFoundException
//        ↓
//assertThrows bắt được, so loại: có đúng StudentNotFoundException không?
//        ↓
//   Đúng  → test xanh
//   Không ném, hoặc ném loại khác → test đỏ
    }
    @Test
    void update_khiKhongTimThay_nemStudentNotFoundException() {
        when(studentRepository.findById(999)).thenReturn(Optional.empty());
        StudentRequest request = new StudentRequest();
        request.setName("X");
        request.setAge(20);
        assertThrows(StudentNotFoundException.class, () -> studentService.update(999, request));
//        assertThrows luon chay dau tien
    }

    @Test
    void create_luuStudentVaTraVeResponse() {
        StudentRequest request = new StudentRequest();
        request.setName("Binh");
        request.setAge(22);

        Student saved = new Student();
        saved.setId(5);
        saved.setName("Binh");
        saved.setAge(22);
        when(studentRepository.save(any(Student.class))).thenReturn(saved);

        StudentResponse result = studentService.create(request);

        assertEquals( 5, result.getId());
        assertEquals("Binh", result.getName());
        verify(studentRepository).save(any(Student.class));
//        1. when(...)                        → dặn mock
//2. studentService.create(request)   → chạy Service
//3. verify(...)                      → "vừa rồi save() có được gọi không?"

//        Quên gọi save (nhờ verify); trả object tự tạo thay vì kết quả của save (id sai); convert DTO sai
    }
    @Test
    void getAll_khiCoHaiStudent_traVeHaiStudentResponse(){
        Student s1 = new Student();
        s1.setId(1);
        s1.setName("An");
        s1.setAge(20);

        Student s2 = new Student();
        s2.setId(2);
        s2.setName("Binh");
        s2.setAge(22);
        when(studentRepository.findAll()).thenReturn(List.of(s1, s2));
//        Quy tắc: nếu ai gọi findAll()  →  trả về List.of(s1, s2)
//        (chưa trả gì)

        List<StudentResponse> result = studentService.getAll();
//        Service gọi findAll() trong ham getALL thật → LÚC NÀY mock mới trả [s1, s2]

        assertEquals(2, result.size());
        assertEquals("An", result.get(0).getName());
        assertEquals("Binh", result.get(1).getName());
    }

}