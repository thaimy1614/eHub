package com.datn.school_service.Repository;

import com.datn.school_service.Models.SchoolYear;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SchoolYearRepository extends JpaRepository<SchoolYear, Long> {
    boolean existsSchoolBySchoolYear(String schoolYear);

    SchoolYear findSchoolYearBySchoolYear(String schoolYear);
}
