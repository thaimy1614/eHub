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
public class ClassRequest {


    @Column(name = "ClassName", length = 50)
    private String className;

    @Column(name = "SemesterId", length = 50)
    private Long semesterId;

//    @Column(name = "TeacherId", length = 50)
//    private String teacherId;

    @Column(name = "SchoolYearId", length = 50)
    private Long schoolYearId;

    @Column(name = "HeadTeacherId", length = 50)
    private String headTeacherId;
    @Column(name = "ClassMonitorId", length = 50)
    private String classMonitorId;

}
