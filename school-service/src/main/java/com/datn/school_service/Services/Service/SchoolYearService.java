package com.datn.school_service.Services.Service;

import com.datn.school_service.Dto.Request.SchoolYearRequest;
import com.datn.school_service.Dto.Respone.SchoolYearResponse;
import com.datn.school_service.Exceptions.AppException;
import com.datn.school_service.Exceptions.ErrorCode;
import com.datn.school_service.Mapper.SchoolYearMapper;
import com.datn.school_service.Models.SchoolYear;
import com.datn.school_service.Repository.SchoolYearRepository;
import com.datn.school_service.Services.InterfaceService.SchoolYearServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SchoolYearService implements SchoolYearServiceInterface {
    final SchoolYearRepository schoolYearRepository;
    final SchoolYearMapper schoolYearMapper;

    @Override
    public List<SchoolYearResponse> getSchoolYears() {
        List<SchoolYear> schoolYeares = schoolYearRepository.findAll();
        if (schoolYeares.isEmpty()) {
            throw new AppException(ErrorCode.ENTITY_EMPTY, "SchoolYear");
        }

        return schoolYeares.stream()
                .map(schoolYearMapper::toSchoolYearResponse)
                .collect(Collectors.toList());
    }

    @Override
    public SchoolYearResponse updateSchoolYears(Long ID, SchoolYearRequest schoolYearRequest) {
        try {
            // Tìm đối tượng
            SchoolYear schoolYeares = schoolYearRepository.findById(ID)
                    .orElseThrow(() -> new AppException(ErrorCode.ENTITYS_NOT_FOUND, "schoolYear", ID));

            // Cập nhật đối tượng
            schoolYearMapper.updateSchoolYear(schoolYeares, schoolYearRequest);

            // Lưu đối tượng
            return schoolYearMapper.toSchoolYearResponse(schoolYearRepository.saveAndFlush(schoolYeares));
        } catch (DataAccessException e) {
            // Log lỗi và ném exception tùy chỉnh
            throw new AppException(ErrorCode.FAILED_SAVE_ENTITY, "schoolYear");
        }
    }





    @Override
    public SchoolYearResponse addSchoolYear(SchoolYearRequest schoolYearRequest) {
        return null;
    }

    @Override
    public SchoolYearResponse findByID(Long id) {
        SchoolYear schoolYeares = schoolYearRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ENTITYS_NOT_FOUND, "schoolYear", id));
        return schoolYearMapper.toSchoolYearResponse(schoolYeares);
    }
}
