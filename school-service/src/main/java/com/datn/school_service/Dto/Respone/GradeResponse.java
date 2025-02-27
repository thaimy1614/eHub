package com.datn.school_service.Dto.Respone;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Nationalized;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GradeResponse {
    @Nationalized
    @Column(name = "GradeId", nullable = false, length = 50)
    private Long gradeId;

    @Nationalized
    @Column(name = "GradeName", length = 50)
    private String gradeName;
}
