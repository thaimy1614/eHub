package com.datn.notification_service.service.message;

import com.datn.notification_service.dto.kafka.AccountInfo;
import com.datn.notification_service.dto.kafka.SendOTP;
import com.datn.notification_service.dto.kafka.SendPassword;
import com.datn.notification_service.dto.kafka.VerifyAccount;
import org.springframework.kafka.annotation.KafkaListener;

public interface MessageService {
    @KafkaListener(id = "sendOtpGroup", topics = "sendOTP")
    void listenSendOtp(SendOTP sendOtp);

    @KafkaListener(id = "sendPasswordGroup", topics = "sendNewPassword")
    void listenSendPassword(SendPassword sendPassword);

    @KafkaListener(id = "verificationGroup", topics = "verification")
    void listenVerification(VerifyAccount verifyAccount);

    @KafkaListener(id = "sendAccountInfoGroup", topics = "sendAccountInfo")
    void listenSendAccountInfo(AccountInfo accountInfo);

}
