package com.datn.school_service.Mapper;

import com.datn.school_service.Dto.Request.SchoolRequest;
import com.datn.school_service.Dto.Respone.SchoolResponse;
import com.datn.school_service.Models.School;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SchoolMapper {
    SchoolResponse toSchoolRespone(School school);

    School toSchool(SchoolRequest schoolResRequest);

    void updateSchool(@MappingTarget School clazz, SchoolRequest schoolRequest);
}
