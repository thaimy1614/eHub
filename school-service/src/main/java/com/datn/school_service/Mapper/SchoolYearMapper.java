package com.datn.school_service.Mapper;

import com.datn.school_service.Dto.Request.SchoolYearRequest;
import com.datn.school_service.Dto.Respone.SchoolYearResponse;
import com.datn.school_service.Models.SchoolYear;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface SchoolYearMapper {
    SchoolYearResponse toSchoolYearResponse(SchoolYear schoolyear);

    SchoolYear toSchoolYear(SchoolYearRequest schoolYearRequest);

    void updateSchoolYear(@MappingTarget SchoolYear schoolyear, SchoolYearRequest schoolYearRequest);
}
