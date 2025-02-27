package com.datn.user_service.controller;

import com.datn.user_service.dto.ApiResponse;
import com.datn.user_service.dto.request.*;
import com.datn.user_service.dto.response.*;
import com.datn.user_service.service.account.AccountService;
import com.nimbusds.jose.JOSEException;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.view.RedirectView;

import java.net.MalformedURLException;

@RestController
@RequiredArgsConstructor
@RequestMapping("${application.api.prefix}/identity")
public class IdentityController {
    private final AccountService accountService;

    @PostMapping("/outbound/authentication")
    ApiResponse<LoginResponse> outboundAuthenticate(
            @RequestParam("code") String code
    ) throws JOSEException, MalformedURLException {
        var result = accountService.outboundAuthenticate(code);
        return ApiResponse.<LoginResponse>builder().message("Login with google successfully!").result(result).build();
    }

    @PostMapping("/login")
    ApiResponse<LoginResponse> login(@RequestBody LoginRequest request) throws Exception {
        var auth = accountService.authenticate(request);
        return ApiResponse.<LoginResponse>builder()
                .message("Login successfully!")
                .result(auth)
                .build();
    }

    @PostMapping("/register/one")
    ApiResponse<RegisterResponse> registerOne(@RequestBody RegisterUser request) {
        RegisterResponse response = accountService.registerOne(request);
        return ApiResponse.<RegisterResponse>builder().message("Register successfully!").result(response).build();
    }

    @PostMapping(
            path = "/register/batch",
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    ApiResponse<RegisterResponse> registerBatch(@RequestParam("file") MultipartFile file, @RequestParam("type") String type) {
        RegisterResponse response = accountService.registerBatch(file, type);
        return ApiResponse.<RegisterResponse>builder()
                .message("Signup successfully, please go to email to verify your email address.")
                .result(response)
                .build();
    }

    @PostMapping("/introspect")
    ApiResponse<IntrospectResponse> introspect(
            @RequestBody IntrospectRequest request
    ) {
        IntrospectResponse response = accountService.introspect(request.getToken());
        return ApiResponse.<IntrospectResponse>builder()
                .result(response)
                .build();
    }

    @PostMapping("/logout")
    ApiResponse<Object> logout(@RequestBody LogoutRequest request) throws Exception {
        accountService.logout(request.getToken());
        return ApiResponse.builder().message("Logout successfully!").result(true).build();
    }

    @PostMapping("/refresh")
    ApiResponse<RefreshTokenResponse> refreshToken(@RequestBody RefreshTokenRequest request) throws Exception {
        RefreshTokenResponse response = accountService.refreshToken(request);
        return ApiResponse.<RefreshTokenResponse>builder()
                .result(response)
                .build();
    }

    @GetMapping("/verify")
    RedirectView verifyAccount(
            @RequestParam("email") String email,

            @RequestParam("token") String token
    ) {
        final String redirectUrl = accountService.verifyAccount(email, token);
        return new RedirectView(redirectUrl);
    }

    @PostMapping("/forget-password/get-otp")
    ApiResponse<SendOTPResponse> sendOtp(@RequestBody SendOTPRequest request) {
        SendOTPResponse response = accountService.sendOTPForForgetPassword(request.getEmail());
        return ApiResponse.<SendOTPResponse>builder()
                .result(response)
                .build();
    }

    @PostMapping("/forget-password/check-otp")
    ApiResponse<CheckOTPResponse> checkOtp(@RequestBody CheckOTPRequest request) {
        CheckOTPResponse response = accountService.checkOTP(request.getOtp(), request.getEmail());
        return ApiResponse.<CheckOTPResponse>builder()
                .result(response)
                .build();
    }
}
