package com.datn.school_service.Dto.Respone;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ClassResponse {

    private Long classId;

    private String className;

    private Long semesterId;

    private String teacherId;

    private Long schoolYearId;

//    @Column(name = "HeadTeacherId", length = 50)
//    private UUID headTeacherId;

    private String classMonitorId;
}

