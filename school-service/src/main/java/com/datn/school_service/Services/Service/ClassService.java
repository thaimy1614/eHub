package com.datn.school_service.Services.Service;

import com.datn.school_service.Dto.Request.ClassRequest;
import com.datn.school_service.Dto.Request.HeadTeacherClassUpdate;
import com.datn.school_service.Dto.Respone.ClassResponse;
import com.datn.school_service.Exceptions.AppException;
import com.datn.school_service.Exceptions.ErrorCode;
import com.datn.school_service.Mapper.ClassMapper;
import com.datn.school_service.Models.Class;
import com.datn.school_service.Repository.ClassRepository;
import com.datn.school_service.Services.InterfaceService.ClassServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ClassService implements ClassServiceInterface {

    final ClassRepository classRepository;
    final ClassMapper classMapper;

    @Override
    public List<ClassResponse> getClasses() {
        List<Class> classes = classRepository.findAll();
        if (classes.isEmpty()) {
            throw new AppException(ErrorCode.ENTITY_EMPTY, "Class");
        }

        return classes.stream()
                .map(classMapper::toClassRespone)
                .collect(Collectors.toList());
    }


    @Override
    public ClassResponse updateClasses(Long ID, ClassRequest classRequest) {
        try {
            // Tìm đối tượng
            Class classes = classRepository.findById(ID)
                    .orElseThrow(() -> new AppException(ErrorCode.ENTITYS_NOT_FOUND, "class", ID));

            // Cập nhật đối tượng
            classMapper.updateClass(classes, classRequest);

            // Lưu đối tượng
            return classMapper.toClassRespone(classRepository.saveAndFlush(classes));
        } catch (DataAccessException e) {
            // Log lỗi và ném exception tùy chỉnh
            throw new AppException(ErrorCode.FAILED_SAVE_ENTITY, "class");
        }
    }
    @Override
    public ClassResponse findByID(Long id) {
        Class classes = classRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ENTITYS_NOT_FOUND, "class", id));
        return classMapper.toClassRespone(classes);
    }

    @Override
    public ClassResponse setHeadTeacher(Long id, HeadTeacherClassUpdate headTeacherClassUpdate) {
        if (headTeacherClassUpdate == null) {
            throw new AppException(ErrorCode.INVALID_REQUEST, " headTeacherId is null");
        }

        // Tìm đối tượng class
        Class clazz = classRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ENTITYS_NOT_FOUND, "class", id));

        // Sử dụng mapper để cập nhật headTeacher
        classMapper.setHeadTeacher(clazz, headTeacherClassUpdate);

        // Lưu lại đối tượng đã cập nhật
        try {
            Class updatedClass = classRepository.save(clazz);
            return classMapper.toClassRespone(updatedClass);
        } catch (Exception e) {

            throw new AppException(ErrorCode.FAILED_SAVE_ENTITY, "class");
        }
    }


    @Override
    public ClassResponse addClass(ClassRequest classRequest) {
        Class clas = classMapper.toClass(classRequest);
        try {
            //   classRepository.saveAndFlush(clas);
            return classMapper.toClassRespone(classRepository.saveAndFlush(clas));
        } catch (DataIntegrityViolationException e) {
            throw new AppException(ErrorCode.FAILED_SAVE_ENTITY, "class");
        }

    }

}
