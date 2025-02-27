package com.datn.school_service.Dto.Respone;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SemesterResponse {
    private String semesterId;
    private String semesterName;
}
