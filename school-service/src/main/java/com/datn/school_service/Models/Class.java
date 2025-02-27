package com.datn.school_service.Models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Class {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "class_id")
    private Long classId;

    @Column(name = "class_name", length = 50)
    private String className;

    @Column(name = "semester_id", length = 50)
    private Long semesterId;

    @Column(name = "teacher_id", length = 50)
    private String teacherId;

    @Column(name = "school_year_id", length = 50)
    private Long schoolYearId;

//    @Column(name = "HeadTeacherId", length = 50)
//    private UUID headTeacherId;

    @Column(name = "class_monitor_id", length = 50)
    private String classMonitorId;

}