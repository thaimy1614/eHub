package com.datn.school_service.Services.InterfaceService;

import com.datn.school_service.Dto.Request.SchoolRequest;
import com.datn.school_service.Dto.Respone.SchoolResponse;

import java.util.List;


public interface SchoolServiceInterface {
    SchoolResponse getSchoolInfo();

    SchoolResponse updateSchool(Long ID, SchoolRequest schoolRequest);

    List<SchoolResponse> getAllSchools();
}
