package com.datn.school_service.Services.InterfaceService;

import com.datn.school_service.Dto.Request.SemesterRequest;
import com.datn.school_service.Dto.Respone.SemesterResponse;

import java.util.List;


public interface SemesterServiceInterface {
    List<SemesterResponse> getSemesters();

    SemesterResponse updateSemesters(Long ID, SemesterRequest semesterRequest);

//    SemesterResponse addSemester(SemesterRequest semesterRequest);

    SemesterResponse findByID(Long id);
}
