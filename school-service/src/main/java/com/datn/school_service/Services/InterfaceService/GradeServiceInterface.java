package com.datn.school_service.Services.InterfaceService;

import com.datn.school_service.Dto.Request.GradeRequest;
import com.datn.school_service.Dto.Respone.GradeResponse;

import java.util.List;


public interface GradeServiceInterface {
    List<GradeResponse> getGradees();

    GradeResponse updateGradees(Long ID, GradeRequest gradeRequest);

    GradeResponse addGrade(GradeRequest gradeRequest);

    GradeResponse findByID(Long id);
}
