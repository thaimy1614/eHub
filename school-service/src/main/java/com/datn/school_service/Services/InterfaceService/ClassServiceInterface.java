package com.datn.school_service.Services.InterfaceService;

import com.datn.school_service.Dto.Request.ClassRequest;
import com.datn.school_service.Dto.Request.HeadTeacherClassUpdate;
import com.datn.school_service.Dto.Respone.ClassResponse;

import java.util.List;


public interface ClassServiceInterface {
    List<ClassResponse> getClasses();

    ClassResponse updateClasses(Long ID, ClassRequest classRequest);

    ClassResponse addClass(ClassRequest classRequest);

    ClassResponse findByID(Long id);

    ClassResponse setHeadTeacher(Long id, HeadTeacherClassUpdate headTeacherClassUpdate);
}
