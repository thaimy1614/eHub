package com.datn.user_service.dto.response;

import com.datn.user_service.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.Instant;
import java.time.LocalDate;

@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private String userId;
    private String fullName;
    private String image;
    private String email;
    private String phoneNumber;
    private String address;
    private User.Gender gender;
    private LocalDate dateOfBirth;
    private Instant createdAt;
}
