package com.datn.school_service.Configs;

import com.datn.school_service.Exceptions.AppException;
import com.datn.school_service.Exceptions.ErrorCode;
import com.datn.school_service.Models.Class;
import com.datn.school_service.Models.Grade;
import com.datn.school_service.Models.School;
import com.datn.school_service.Models.SchoolYear;
import com.datn.school_service.Models.Semester;
import com.datn.school_service.Repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataInitializer implements CommandLineRunner {
    @Autowired
    private GradeRepository gradeRepository;

    @Autowired
    private SemesterRepository semesterRepository;

    @Autowired
    private SchoolYearRepository schoolYearRepository;

    @Autowired
    private ClassRepository classRepository;

    @Autowired
    private SchoolRepository schoolRepository;

    @Override
    public void run(String... args) throws Exception {

        // Insert vào bảng Grade
        if (gradeRepository.count() == 0) {
//            if(gradeRepository.existsGradeByGradeName("10"))
//            {
//
//            }
            gradeRepository.saveAll(List.of(
                    new Grade(null, "10"),
                    new Grade(null, "11"),
                    new Grade(null, "12")
            ));
            System.out.println("Dữ liệu Grade đã được thêm.");
        } else {
            System.out.println("Dữ liệu Grade đã tồn tại. Không thêm lại.");
        }

        // Insert vào bảng Semester
        if (semesterRepository.count() == 0) {
            semesterRepository.saveAll(List.of(
                    new Semester(null, "Học kỳ 1"),
                    new Semester(null, "Học kỳ 2")
            ));
            System.out.println("Dữ liệu Semester đã được thêm.");
        } else {
            System.out.println("Dữ liệu Semester đã tồn tại. Không thêm lại.");
        }

        // Insert vào bảng SchoolYear
        if (schoolYearRepository.count() == 0) {
            schoolYearRepository.saveAll(List.of(
                    new SchoolYear(null, "2023-2024"),
                    new SchoolYear(null, "2024-2025"),
                    new SchoolYear(null, "2025-2026")
            ));
            System.out.println("Dữ liệu SchoolYear đã được thêm.");
        } else {
            System.out.println("Dữ liệu SchoolYear đã tồn tại. Không thêm lại.");
        }

        // Insert vào bảng School
        if (schoolRepository.count() == 0) {
            schoolRepository.save(new School(
                    null,
                    "THPT Bùi Hữu Nghĩa",
                    "55 đường CMT 8, P An Thới, Quận Bình Thuỷ, Thành phố Cần Thơ, Việt Nam",
                    null,
                    null
            ));
            System.out.println("Dữ liệu School đã được thêm.");
        } else {
            System.out.println("Dữ liệu School đã tồn tại. Không thêm lại.");
        }

        // Insert vào bảng Class
        if (classRepository.count() == 0) {
            SchoolYear schoolYear2025 = schoolYearRepository.findSchoolYearBySchoolYear("2024-2025");
            Semester semester1 = semesterRepository.findSemesterBySemesterName("Học kỳ 1");
            if (schoolYear2025 == null || semester1 == null) {
              throw new AppException(ErrorCode.ENTITY_EMPTY,"semester or school year");
              //  System.out.println("Không thể thêm dữ liệu vào Class vì SchoolYear hoặc Semester không tồn tại.");
               // return;
            }
            if (schoolYear2025 != null && semester1 != null)
            {
                for (int grade = 10; grade <= 12; grade++) {
                    for (int i = 1; i <= 10; i++) {
                        String className = grade + "A" + i;
                        classRepository.save(new Class(
                                null,
                                className,
                                semester1.getSemesterId(),
                                null,
                                schoolYear2025.getSchoolYearId(),
                                null
                        ));
                    }
                }
                System.out.println("Dữ liệu Class đã được thêm.");
            } else {
                System.out.println("Không thể thêm dữ liệu vào Class vì SchoolYear hoặc Semester không tồn tại.");
            }
        } else {
            System.out.println("Dữ liệu Class đã tồn tại. Không thêm lại.");
        }

        System.out.println("Dữ liệu đã được kiểm tra và xử lý xong.");
    }

}
