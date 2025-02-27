package com.datn.user_service.dto.request;

import com.datn.user_service.model.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRequest {
    private String fullName;

    private String email;

    private User.Gender gender;

    private String phoneNumber;

    private String address;

    private LocalDate dateOfBirth;
}
