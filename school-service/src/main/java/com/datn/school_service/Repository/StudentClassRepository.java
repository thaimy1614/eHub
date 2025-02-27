package com.datn.school_service.Repository;

import com.datn.school_service.Models.StudentClass;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface StudentClassRepository extends JpaRepository<StudentClass, Long> {
    boolean existsByStudentIdAndClassId(String studentId, Long classId);
}
