package com.datn.school_service.Services.Service;

import com.datn.school_service.Dto.Request.SemesterRequest;
import com.datn.school_service.Dto.Respone.SemesterResponse;
import com.datn.school_service.Exceptions.AppException;
import com.datn.school_service.Exceptions.ErrorCode;
import com.datn.school_service.Mapper.SemesterMapper;
import com.datn.school_service.Models.Semester;
import com.datn.school_service.Repository.SemesterRepository;
import com.datn.school_service.Services.InterfaceService.SemesterServiceInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SemesterService implements SemesterServiceInterface {
    final SemesterRepository semesterRepository;
    final SemesterMapper semesterMapper;

    @Override
    public List<SemesterResponse> getSemesters() {
        List<Semester> semesteres = semesterRepository.findAll();
        if (semesteres.isEmpty()) {
            throw new AppException(ErrorCode.ENTITY_EMPTY, "Semester");
        }

        return semesteres.stream()
                .map(semesterMapper::toSemesterRespone)
                .collect(Collectors.toList());
    }


    @Override
    public SemesterResponse updateSemesters(Long ID, SemesterRequest semesterRequest) {
        try {
            // Tìm đối tượng
            Semester semesteres = semesterRepository.findById(ID)
                    .orElseThrow(() -> new AppException(ErrorCode.ENTITYS_NOT_FOUND, "semester", ID));

            // Cập nhật đối tượng
            semesterMapper.updateSemester(semesteres, semesterRequest);

            // Lưu đối tượng
            return semesterMapper.toSemesterRespone(semesterRepository.saveAndFlush(semesteres));
        } catch (DataAccessException e) {
            // Log lỗi và ném exception tùy chỉnh
            throw new AppException(ErrorCode.FAILED_SAVE_ENTITY, "semester");
        }
    }

    @Override
    public SemesterResponse findByID(Long id) {
        Semester semesteres = semesterRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.ENTITYS_NOT_FOUND, "semester", id));
        return semesterMapper.toSemesterRespone(semesteres);
    }

//    @Override
//    public SemesterResponse addSemester(SemesterRequest semesterRequest) {
//        return null;
//    }
}
