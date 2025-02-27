package com.datn.user_service.mapper;

import com.datn.user_service.dto.request.RegisterTeacherRequest;
import com.datn.user_service.dto.response.TeacherResponse;
import com.datn.user_service.model.Teacher;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TeacherMapper {
    Teacher toTeacher(RegisterTeacherRequest request);

    TeacherResponse toTeacherResponse(Teacher teacher);
}
