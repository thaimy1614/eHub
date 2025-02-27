package com.datn.school_service.Services.InterfaceService;

import com.datn.school_service.Dto.Request.SchoolYearRequest;
import com.datn.school_service.Dto.Respone.SchoolYearResponse;

import java.util.List;


public interface SchoolYearServiceInterface {
    List<SchoolYearResponse> getSchoolYears();

    SchoolYearResponse updateSchoolYears(Long ID, SchoolYearRequest schoolYearRequest);

    SchoolYearResponse addSchoolYear(SchoolYearRequest schoolYearRequest);

    SchoolYearResponse findByID(Long id);
}
