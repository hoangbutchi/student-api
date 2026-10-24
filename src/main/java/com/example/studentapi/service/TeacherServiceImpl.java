package com.example.studentapi.service;

import com.example.studentapi.dto.PageResponse;
import com.example.studentapi.dto.TeacherRequest;
import com.example.studentapi.dto.TeacherResponse;
import com.example.studentapi.entity.Teacher;
import com.example.studentapi.exception.TeacherNotFoundException;
import com.example.studentapi.repository.TeacherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
public class TeacherServiceImpl implements TeacherService {

    @Autowired
    private TeacherRepository TeacherRepository;

    // Convert Entity -> Response DTO
    private TeacherResponse toResponse(Teacher Teacher) {
        return new TeacherResponse(Teacher.getId(), Teacher.getName(), Teacher.getSubject());
    }
//    dc them private tùy í,nhưng public phải theo Teacherservice

    @Override
    public List<TeacherResponse> getAll() {
        return TeacherRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
    @Override
    public TeacherResponse getById(Integer id) {
        Teacher Teacher = TeacherRepository.findById(id)
                .orElseThrow(() -> new TeacherNotFoundException(id));
        return toResponse(Teacher);
    }

    @Override
    public TeacherResponse create(TeacherRequest request) {
        Teacher Teacher = new Teacher();
        Teacher.setName(request.getName());
        Teacher.setSubject(request.getSubject());
        Teacher saved = TeacherRepository.save(Teacher);
        return toResponse(saved);
    }

    @Override
    public TeacherResponse update(Integer id, TeacherRequest request) {
        Teacher Teacher = TeacherRepository.findById(id)
                .orElseThrow(() -> new TeacherNotFoundException(id));

        Teacher.setName(request.getName());
        Teacher.setSubject(request.getSubject());
        Teacher updated = TeacherRepository.save(Teacher);
        return toResponse(updated);
    }

    @Override
    public void delete(Integer id) {
        TeacherRepository.deleteById(id);
    }

    @Override
    public List<TeacherResponse> searchByName(String name) {
        return TeacherRepository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }


    @Override
    public PageResponse<TeacherResponse> getAllPaged(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending());

//                Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).ascending()); ham  nay tu co khi import
        Page<Teacher> TeacherSubject = TeacherRepository.findAll(pageable);
//        pSubject co mang gia tri stuendt(id,name,Subject) va ca gia tri  findAll(pSubjectable)
//        <> la wrapper chua nhieu thu
//        Giả sử database có 12 sinh viên, bạn gọi với pSubjectable = (pSubject=0, size=5, sort theo name):
//
//
//TeacherPSubject = {
//    content         = [Teacher1, Teacher2, Teacher3, Teacher4, Teacher5],  // 5 sinh viên của trang 0
//    number          = 0,          // trang hiện tại
//    size            = 5,          // kích thước mỗi trang
//    totalElements   = 12,         // tổng số sinh viên trong TOÀN BỘ database
//    totalPSubjects      = 3,          // tổng số trang (ceil(12/5) = 3)
//    first           = true,       // có phải trang đầu tiên không
//    last            = false,      // có phải trang cuối cùng không
//    numberOfElements = 5          // số phần tử THẬT SỰ có trong trang này
//}
//        -- Câu 1: lấy đúng 5 dòng của trang 0 (dựa vào thông tin trong pSubjectable)
//SELECT * FROM Teachers ORDER BY name ASC LIMIT 5 OFFSET 0;
//
//-- Câu 2: đếm tổng số dòng trong bảng
//SELECT COUNT(*) FROM Teachers;

        List<TeacherResponse> data = TeacherSubject.getContent()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());

        return new PageResponse<>(
                data,
                TeacherSubject.getNumber(),
                TeacherSubject.getSize(),
                TeacherSubject.getTotalElements(),
                TeacherSubject.getTotalPages()
        );
    }
}