package com.datn.user_service.dto.request;

import com.datn.user_service.model.ParentStudent;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterParentRequest extends RegisterUser {
    private String studentEmail;
    private Boolean isNotificationOn;
    private ParentStudent.ParentType parentType;
}
