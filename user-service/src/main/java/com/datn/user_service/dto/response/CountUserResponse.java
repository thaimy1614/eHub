package com.datn.user_service.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CountUserResponse {
    Long total;
    Long numberOfStudents;
    Long numberOfTeachers;
    Long numberOfParents;
}
