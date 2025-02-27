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
public class School {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "school_id")
    private Long schoolID;

    @Column(name = "school_name", length = 200)
    private String schoolName;

    @Column(name = "school_address", length = 500)
    private String schoolAddress;

    @Column(name = "principal_user_id", length = 50, nullable = true)
    private String principalUserID;

    @Column(name = "vice_principal_user_id", length = 50, nullable = true)
    private String vicePrincipalUserID;

}