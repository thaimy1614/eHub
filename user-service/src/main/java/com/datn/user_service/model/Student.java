package com.datn.user_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "students")
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Student extends User {
    @Column(name = "student_id")
    private String studentId; // CE171563

    @OneToMany(mappedBy = "student")
    private List<ParentStudent> parents;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "students_classes",
            joinColumns = @JoinColumn(name = "student_id")
    )
    @Column(name = "class_id")
    private List<String> classIds;
}