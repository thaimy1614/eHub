package com.datn.school_service.Services.InterfaceService;

import com.datn.school_service.Dto.Request.StudentClassRequest;
import com.datn.school_service.Dto.Respone.StudentClassResponse;

public interface StudentClassInterface {
    StudentClassResponse addStudenttoClass(StudentClassRequest studentClassRequest);

}
