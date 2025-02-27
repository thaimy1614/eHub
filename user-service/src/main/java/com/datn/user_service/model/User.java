package com.datn.user_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.io.Serializable;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;

@Table(name = "users")
@Inheritance(strategy = InheritanceType.JOINED)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User implements Serializable {
    @Id
    @Column(name = "user_id")
    private String userId;

    @Column(name = "full_name")
    private String fullName;

    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "gender")
    private Gender gender;

    @Column(name = "phone_number")
    private String phoneNumber;

    private String address;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    private String image;

    @CreationTimestamp
    @Column(name = "create_at")
    private Instant createAt;

    @UpdateTimestamp
    @Column(name = "update_at")
    private Instant updateAt;

    public enum Gender {
        MALE,
        FEMALE,
        OTHER
    }
}
