package com.datn.school_service.Dto.Request;

import jakarta.persistence.Column;

public class SchoolYearRequest {

    @Column(name = "SchoolYear")
    private String schoolYear;
}
