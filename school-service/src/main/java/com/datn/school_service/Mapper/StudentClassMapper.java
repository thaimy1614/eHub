package com.datn.school_service.Mapper;

import com.datn.school_service.Dto.Request.StudentClassRequest;
import com.datn.school_service.Dto.Respone.StudentClassResponse;
import com.datn.school_service.Models.StudentClass;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface StudentClassMapper {
    StudentClassResponse toStudentClassRespone(StudentClass studentClass);

    StudentClass toStudentClass(StudentClassRequest studentclassRespone);

    void updateStudentClass(@MappingTarget StudentClass clazz, StudentClassRequest studentClassRequest);
}
