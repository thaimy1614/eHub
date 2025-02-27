package com.datn.notification_service.service.email;

import com.datn.notification_service.dto.kafka.AccountInfo;
import com.datn.notification_service.dto.kafka.SendOTP;
import com.datn.notification_service.dto.kafka.SendPassword;

public interface EmailService {
    void sendOtp(SendOTP sendOtp);

    void sendNewPassword(SendPassword sendPassword);

    void sendVerification(String name, String to, String url);

    void sendAccountInfo(AccountInfo accountInfo);
}

