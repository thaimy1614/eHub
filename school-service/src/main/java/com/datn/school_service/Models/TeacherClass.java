package com.datn.school_service.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "teachers_classes")
public class TeacherClass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "teacher_class_id", nullable = false, length = 50)
    private Long teacherClassId;

    @Column(name = "class_id", length = 50)
    private Long classId;


    @Column(name = "teacher_id", length = 50)
    private String teacherId;

}