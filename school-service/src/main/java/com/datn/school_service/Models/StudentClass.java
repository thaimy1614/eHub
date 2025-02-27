package com.datn.school_service.Models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "students_classes")
public class StudentClass {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "class_student_id", length = 50)
    private Long classStudentId;


    @Column(name = "student_id", length = 50)
    private String studentId;


    @Column(name = "class_id", length = 50)
    private Long classId;

}