package com.datn.school_service.Services.Service;

import com.datn.school_service.Dto.Request.GradeRequest;
import com.datn.school_service.Dto.Respone.GradeResponse;
import com.datn.school_service.Exceptions.AppException;
import com.datn.school_service.Exceptions.ErrorCode;
import com.datn.school_service.Mapper.GradeMapper;
import com.datn.school_service.Models.Grade;
import com.datn.school_service.Repository.GradeRepository;
import com.datn.school_service.Services.InterfaceService.GradeServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GradeService implements GradeServiceInterface {

    final GradeRepository gradeRepository;
    final GradeMapper gradeMapper;

    @Override
    public List<GradeResponse> getGradees() {
        List<Grade> gradees = gradeRepository.findAll();
        if (gradees.isEmpty()) {
            throw new AppException(ErrorCode.ENTITY_EMPTY, "Grade");
        }

        return gradees.stream()
                .map(gradeMapper::toGradeRespone)
                .collect(Collectors.toList());
    }

    @Override
    public GradeResponse updateGradees(Long ID, GradeRequest gradeRequest) {
        try {
            // Tìm đối tượng
            Grade gradees = gradeRepository.findById(ID)
                    .orElseThrow(() -> new AppException(ErrorCode.ENTITYS_NOT_FOUND, "grade", ID));

            // Cập nhật đối tượng
            gradeMapper.updateGrade(gradees, gradeRequest);

            // Lưu đối tượng
            return gradeMapper.toGradeRespone(gradeRepository.saveAndFlush(gradees));
        } catch (DataAccessException e) {
            // Log lỗi và ném exception tùy chỉnh
            throw new AppException(ErrorCode.FAILED_SAVE_ENTITY, "grade");
        }
    }




    @Override
    public GradeResponse findByID(Long id) {
        Grade gradees = gradeRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ENTITYS_NOT_FOUND, "grade", id));
        return gradeMapper.toGradeRespone(gradees);
    }

    @Override
    public GradeResponse addGrade(GradeRequest gradeRequest) {
        return null;
    }
}
