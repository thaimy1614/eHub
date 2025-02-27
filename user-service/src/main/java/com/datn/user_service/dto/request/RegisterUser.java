package com.datn.user_service.dto.request;

import com.datn.user_service.model.User;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@JsonTypeInfo(use = JsonTypeInfo.Id.NAME, include = JsonTypeInfo.As.EXTERNAL_PROPERTY, property = "role")
@JsonSubTypes({
        @JsonSubTypes.Type(value = RegisterStudentRequest.class, name = "student"),
        @JsonSubTypes.Type(value = RegisterTeacherRequest.class, name = "teacher"),
        @JsonSubTypes.Type(value = RegisterParentRequest.class, name = "parent")
})
@Data
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUser {
    @NotEmpty(message = "Email should not be empty")
    @Email(message = "Email should be valid")
    private String email;
    private String fullName;
    private String phoneNumber;
    private String address;
    private User.Gender gender;
    private LocalDate dateOfBirth;
    private String role;
}
