package com.datn.school_service.Controllers;

import com.datn.school_service.Dto.Request.ClassRequest;
import com.datn.school_service.Dto.Request.HeadTeacherClassUpdate;
import com.datn.school_service.Dto.Respone.ApiResponse;
import com.datn.school_service.Dto.Respone.ClassResponse;
import com.datn.school_service.Services.InterfaceService.ClassServiceInterface;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/class")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class ClassController {

    final ClassServiceInterface classService;

    @GetMapping()
    ApiResponse<List<ClassResponse>> getAllClasses() {
        return ApiResponse.<List<ClassResponse>>builder()
                .result(classService.getClasses())
                .build();

    }

//    @GetMapping("/{id}")
//    ApiResponse<ClassRespone> getClassById(@PathVariable Long id) {
//        return ApiResponse.<ClassRespone>builder()
//                .result(classService.findbyID(id))
//                .build();
//    }

    //    @PostMapping("/update/{id}")
//    ApiResponse<ClassRespone> updateClass(@PathVariable Long id, @RequestBody ClassRequest classRequest) {
//        return ApiResponse.<ClassRespone>builder()
//                .result(classService.updateClasses(id, classRequest))
//                .build();
//    }
    @PostMapping()
    ApiResponse<ClassResponse> addClass(@RequestBody ClassRequest classRequest) {
        return ApiResponse.<ClassResponse>builder()
                .result(classService.addClass(classRequest))
                .build();
    }

    @PostMapping("/set-head-teacher/{id}")
    ApiResponse<ClassResponse> setHeadTeacher(@PathVariable Long id, @RequestBody HeadTeacherClassUpdate headTeacherClassUpdate) {
        return ApiResponse.<ClassResponse>builder()
                .result(classService.setHeadTeacher(id, headTeacherClassUpdate))
                .build();
    }

}