package com.datn.user_service.dto.response;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class RegisterResponse {
    private boolean success;
    private List<Integer> failedResults;
}
