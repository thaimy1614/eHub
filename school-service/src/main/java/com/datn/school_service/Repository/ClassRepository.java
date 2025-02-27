package com.datn.school_service.Repository;

import com.datn.school_service.Models.Class;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ClassRepository extends JpaRepository<Class, Long> {
    boolean existsClassByClassName(String classname);
}
