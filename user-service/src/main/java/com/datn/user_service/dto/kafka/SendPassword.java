package com.datn.user_service.dto.kafka;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SendPassword {
    private String name;
    private String email;
    private String username;
    private String password;
}
