package com.datn.school_service.Repository;

import com.datn.school_service.Models.School;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SchoolRepository extends JpaRepository<School, Long> {

    //School findAll.
}
