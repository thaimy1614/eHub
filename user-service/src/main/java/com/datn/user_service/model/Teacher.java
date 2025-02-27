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
@Table(name = "teachers")
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Teacher extends User {
    private String specialization; // Math

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "teachers_subjects",
            joinColumns = @JoinColumn(name = "teacher_id")
    )
    @Column(name = "subject_id")
    private List<String> subjectIds;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(
            name = "teachers_classes",
            joinColumns = @JoinColumn(name = "teacher_id")
    )
    @Column(name = "class_id")
    private List<String> classIds;
}
