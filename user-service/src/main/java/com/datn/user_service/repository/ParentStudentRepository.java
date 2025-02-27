package com.datn.user_service.repository;

import com.datn.user_service.model.ParentStudent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParentStudentRepository extends JpaRepository<ParentStudent, Long> {
}
