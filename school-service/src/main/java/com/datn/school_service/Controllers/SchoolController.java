package com.datn.school_service.Controllers;

import com.datn.school_service.Dto.Request.SchoolRequest;
import com.datn.school_service.Dto.Respone.ApiResponse;
import com.datn.school_service.Dto.Respone.SchoolResponse;
import com.datn.school_service.Services.Service.SchoolService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/school")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class SchoolController {
    final SchoolService schoolService;

    @PutMapping("/{id}")
    ApiResponse<SchoolResponse> updateSchool(@PathVariable Long id, @RequestBody SchoolRequest schoolRequest) {
        return ApiResponse.<SchoolResponse>builder()
                .result(schoolService.updateSchool(id, schoolRequest))
                .build();
    }

    @GetMapping()
    ApiResponse<SchoolResponse> getSchoolInfo() {
        return ApiResponse.<SchoolResponse>builder()
                .result(schoolService.getSchoolInfo())
                .build();
    }
}
