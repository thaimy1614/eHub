package com.datn.school_service.Mapper;

import com.datn.school_service.Dto.Request.ClassRequest;
import com.datn.school_service.Dto.Request.HeadTeacherClassUpdate;
import com.datn.school_service.Dto.Respone.ClassResponse;
import com.datn.school_service.Models.Class;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ClassMapper {
    ClassResponse toClassRespone(Class clazz);

    Class toClass(ClassRequest classRequest);

    void updateClass(@MappingTarget Class clazz, ClassRequest classRequest);

    Class toClasses(HeadTeacherClassUpdate headTeacherClassUpdate);

    void setHeadTeacher(@MappingTarget Class classs, HeadTeacherClassUpdate headTeacherClassUpdate);
}
