package com.datn.user_service.service;

import com.datn.user_service.dto.request.RegisterUser;
import com.datn.user_service.service.account.AccountService;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

@Service
public class BridgeService {
    private final AccountService accountService;

    public BridgeService(@Lazy AccountService accountService) {
        this.accountService = accountService;
    }

    public void callRegisterUser(RegisterUser registerUser) {
        accountService.registerOne(registerUser);
    }
}
