package com.datn.school_service.Mapper;

import com.datn.school_service.Dto.Request.GradeRequest;
import com.datn.school_service.Dto.Respone.GradeResponse;
import com.datn.school_service.Models.Grade;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface GradeMapper {
    GradeResponse toGradeRespone(Grade clazz);

    Grade toGrade(GradeRequest gradeRequest);

    void updateGrade(@MappingTarget Grade clazz, GradeRequest gradeRequest);
}
