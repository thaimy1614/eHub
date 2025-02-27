package com.datn.school_service.Dto.Request;

import jakarta.persistence.Column;
import org.hibernate.annotations.Nationalized;

public class SemesterRequest {

    @Nationalized
    @Column(name = "SemeterName", length = 50)
    private String semesterName;
}
