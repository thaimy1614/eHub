package com.datn.school_service.Mapper;

import com.datn.school_service.Dto.Request.SemesterRequest;
import com.datn.school_service.Dto.Respone.SemesterResponse;
import com.datn.school_service.Models.Semester;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SemesterMapper {
    SemesterResponse toSemesterRespone(Semester clazz);

    Semester toSemester(SemesterResponse gradeRespone);

    void updateSemester(@MappingTarget Semester clazz, SemesterRequest gradeRequest);
}
