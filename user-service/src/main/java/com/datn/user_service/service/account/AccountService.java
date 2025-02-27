package com.datn.user_service.service.account;

import com.datn.user_service.dto.request.LoginRequest;
import com.datn.user_service.dto.request.RefreshTokenRequest;
import com.datn.user_service.dto.request.RegisterUser;
import com.datn.user_service.dto.response.*;
import com.nimbusds.jose.JOSEException;
import org.springframework.web.multipart.MultipartFile;

import java.net.MalformedURLException;
import java.text.ParseException;

public interface AccountService {
    LoginResponse authenticate(LoginRequest loginRequest) throws JOSEException, MalformedURLException;

    IntrospectResponse introspect(String token);

    RegisterResponse registerOne(RegisterUser signupRequest);

    RegisterResponse registerBatch(MultipartFile file, String type);

    void logout(String token) throws Exception;

    LoginResponse outboundAuthenticate(String code) throws JOSEException, MalformedURLException;

    RefreshTokenResponse refreshToken(RefreshTokenRequest request) throws ParseException, JOSEException, MalformedURLException;

    String verifyAccount(String email, String token);

    SendOTPResponse sendOTPForForgetPassword(String email);

    CheckOTPResponse checkOTP(String otp, String email);
}
