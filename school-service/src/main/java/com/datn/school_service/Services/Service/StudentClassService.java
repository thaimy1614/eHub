package com.datn.school_service.Services.Service;

import com.datn.school_service.Dto.Request.StudentClassRequest;
import com.datn.school_service.Dto.Respone.StudentClassResponse;
import com.datn.school_service.Exceptions.AppException;
import com.datn.school_service.Exceptions.ErrorCode;
import com.datn.school_service.Mapper.StudentClassMapper;
import com.datn.school_service.Models.Class;
import com.datn.school_service.Models.StudentClass;
import com.datn.school_service.Repository.ClassRepository;
import com.datn.school_service.Repository.StudentClassRepository;
import com.datn.school_service.Services.InterfaceService.StudentClassInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentClassService implements StudentClassInterface {

    final StudentClassRepository studentClassRepository;
    final StudentClassMapper studentClassMapper;
    final ClassRepository classRepository;

    @Override
    public StudentClassResponse addStudenttoClass(StudentClassRequest studentClassRequest) {
        // Kiểm tra xem lớp học có tồn tại hay không
        Class clazz = classRepository.findById(studentClassRequest.getClassId())
                .orElseThrow(() -> new AppException(ErrorCode.ENTITYS_NOT_FOUND, "class", studentClassRequest.getClassId()));

        // Tạo đối tượng StudentClass từ request
        StudentClass studentClass = studentClassMapper.toStudentClass(studentClassRequest);

        // Kiểm tra xem sinh viên đã tồn tại trong lớp học hay chưa
        boolean isStudentExists = studentClassRepository.existsByStudentIdAndClassId(
                studentClassRequest.getStudentId(),
                studentClassRequest.getClassId()
        );
        if (isStudentExists) {
            throw new AppException(ErrorCode.ENTITY_ALREADY_EXIT, "Student", "class");
        }

        // Lưu đối tượng StudentClass vào cơ sở dữ liệu
        try {
            StudentClass savedStudentClass = studentClassRepository.save(studentClass);
            return studentClassMapper.toStudentClassRespone(savedStudentClass);
        } catch (Exception e) {
            throw new AppException(ErrorCode.FAILED_SAVE_ENTITY, "studentClass");
        }
    }

}
