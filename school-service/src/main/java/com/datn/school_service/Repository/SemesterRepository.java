package com.datn.school_service.Repository;

import com.datn.school_service.Models.Semester;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SemesterRepository extends JpaRepository<Semester, Long> {
    boolean existsSemesterBySemesterName(String name);

    Semester findSemesterBySemesterName(String name);
}
