package com.datn.notification_service.service.message;

import com.datn.notification_service.dto.kafka.AccountInfo;
import com.datn.notification_service.dto.kafka.SendOTP;
import com.datn.notification_service.dto.kafka.SendPassword;
import com.datn.notification_service.dto.kafka.VerifyAccount;
import com.datn.notification_service.service.email.EmailService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageServiceImpl implements MessageService {
    private final EmailService emailService;

    public void listenSendOtp(SendOTP sendOtp) {
        emailService.sendOtp(sendOtp);
    }

    public void listenSendPassword(SendPassword sendPassword) {
        emailService.sendNewPassword(sendPassword);
    }

    public void listenVerification(VerifyAccount verifyAccount) {
        emailService.sendVerification(verifyAccount.getFullName(), verifyAccount.getEmail(), verifyAccount.getUrl());
    }

    public void listenSendAccountInfo(AccountInfo accountInfo) {
        emailService.sendAccountInfo(accountInfo);
    }
}
