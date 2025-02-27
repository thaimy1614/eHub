package com.datn.notification_service.service.email;

import com.datn.notification_service.dto.kafka.AccountInfo;
import com.datn.notification_service.dto.kafka.SendOTP;
import com.datn.notification_service.dto.kafka.SendPassword;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

@Service
@Slf4j
@RequiredArgsConstructor
class EmailServiceImpl implements EmailService {
    public static final String UTF_8_ENCODING = "UTF-8";
    private final JavaMailSender javaMailSender;
    private final TemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String from;

    @Async
    public void sendVerification(String name, String to, String url) {
        try {
            Context context = new Context();
            context.setVariable("name", name);
            context.setVariable("url", url);
            String text = templateEngine.process("verify-account.html", context);
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, UTF_8_ENCODING);
            helper.setPriority(1);
            helper.setSubject("X HIGH SCHOOL - YOUR ACCOUNT VERIFICATION");
            helper.setFrom(from);
            helper.setTo(to);
            helper.setText(text, true);
            javaMailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    @Async
    public void sendAccountInfo(AccountInfo accountInfo) {
        try {
            Context context = new Context();
            context.setVariable("name", accountInfo.getFullName());
            context.setVariable("username", accountInfo.getUsername());
            context.setVariable("password", accountInfo.getPassword());
            String text = templateEngine.process("send-account.html", context);
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, UTF_8_ENCODING);
            helper.setPriority(1);
            helper.setSubject("X HIGH SCHOOL - YOUR ACCOUNT INFORMATION");
            helper.setFrom(from);
            helper.setTo(accountInfo.getEmail());
            helper.setText(text, true);
            javaMailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Async
    public void sendOtp(SendOTP sendOtp) {
        try {
            Context context = new Context();
            context.setVariable("name", sendOtp.getName());
            context.setVariable("otp", sendOtp.getOtp());
            String text = templateEngine.process("send-otp.html", context);
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, UTF_8_ENCODING);
            helper.setPriority(1);
            helper.setSubject("X HIGH SCHOOL - YOUR OTP FOR RESET PASSWORD");
            helper.setFrom(from);
            helper.setTo(sendOtp.getEmail());
            helper.setText(text, true);
            javaMailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }

    @Async
    public void sendNewPassword(SendPassword sendPassword) {
        try {
            Context context = new Context();
            context.setVariable("name", sendPassword.getName());
            context.setVariable("username", sendPassword.getUsername());
            context.setVariable("password", sendPassword.getPassword());
            String text = templateEngine.process("send-new-password.html", context);
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, UTF_8_ENCODING);
            helper.setPriority(1);
            helper.setSubject("X HIGH SCHOOL - YOUR NEW PASSWORD");
            helper.setFrom(from);
            helper.setTo(sendPassword.getEmail());
            helper.setText(text, true);
            javaMailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }
    }
}
