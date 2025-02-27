package com.datn.school_service.Dto.Request;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GradeRequest {


    @Column(name = "GradeName", length = 50)
    private String gradeName;
}
