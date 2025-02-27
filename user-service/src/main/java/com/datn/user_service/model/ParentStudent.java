package com.datn.user_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "parents_students")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParentStudent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    private Parent parent;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @Column(name = "parent_type")
    @Enumerated(EnumType.STRING)
    private ParentType parentType;

    public enum ParentType {
        FATHER,
        MOTHER,
        GUARDIAN
    }
}

