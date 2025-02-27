package com.datn.school_service.Repository;

import com.datn.school_service.Models.Grade;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface GradeRepository extends JpaRepository<Grade, Long> {
    boolean existsGradeByGradeName(String name);
}
