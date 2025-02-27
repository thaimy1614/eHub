package com.datn.user_service.mapper;

import com.datn.user_service.dto.request.RegisterStudentRequest;
import com.datn.user_service.dto.response.StudentResponse;
import com.datn.user_service.model.Student;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface StudentMapper {
    Student toStudent(RegisterStudentRequest request);

    StudentResponse toStudentResponse(Student student);
}
