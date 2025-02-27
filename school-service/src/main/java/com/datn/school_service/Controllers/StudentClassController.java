package com.datn.school_service.Controllers;

import com.datn.school_service.Dto.Request.StudentClassRequest;
import com.datn.school_service.Dto.Respone.ApiResponse;
import com.datn.school_service.Dto.Respone.StudentClassResponse;
import com.datn.school_service.Services.InterfaceService.StudentClassInterface;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/student-class")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class StudentClassController {
    final StudentClassInterface studentClassService;

    @PostMapping()
    ApiResponse<StudentClassResponse> addStudentToClass(@RequestBody StudentClassRequest studentClass) {
        return ApiResponse.<StudentClassResponse>builder()
                .result(studentClassService.addStudenttoClass(studentClass))
                .build();
    }

}
