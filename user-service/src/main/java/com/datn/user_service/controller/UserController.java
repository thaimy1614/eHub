package com.datn.user_service.controller;

import com.datn.user_service.dto.ApiResponse;
import com.datn.user_service.dto.request.RegisterUser;
import com.datn.user_service.dto.response.CountUserResponse;
import com.datn.user_service.dto.response.UserResponse;
import com.datn.user_service.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("${application.api.prefix}")
public class UserController {
    private final UserService userService;

    @GetMapping("/my-info")
    ApiResponse<UserResponse> getMyInfo(JwtAuthenticationToken jwt) {
        String userId = jwt.getName();
        UserResponse userResponse = userService.getMyInfo(userId);
        return ApiResponse.<UserResponse>builder()
                .message("Get info successfully!")
                .result(userResponse)
                .build();
    }

    @GetMapping("/{userId}")
    ApiResponse<UserResponse> getUserInfoById(@PathVariable String userId) {
        UserResponse userResponse = userService.getMyInfo(userId);
        return ApiResponse.<UserResponse>builder()
                .message("Get user's info successfully!")
                .result(userResponse)
                .build();
    }

    @GetMapping("/count")
    ApiResponse<CountUserResponse> countNumberOfUser() {
        CountUserResponse res = userService.countUsers();
        return ApiResponse.<CountUserResponse>builder()
                .message("Get info successfully!")
                .result(res)
                .build();
    }

    @PutMapping("/{userId}")
    ApiResponse<UserResponse> changeUserProfile(@PathVariable String userId, @RequestBody RegisterUser userRequest) {
        UserResponse response = userService.updateMyInfo(userId, userRequest);
        return ApiResponse.<UserResponse>builder()
                .result(response)
                .build();
    }

    @GetMapping("/search")
    ApiResponse<Page<UserResponse>> searchUsers
            (
                    @RequestParam(defaultValue = "0") int page,
                    @RequestParam(defaultValue = "5") int size,
                    @RequestParam String key
            ) {
        Pageable pageable = PageRequest.of(page, size);
        if (key == null || key.isEmpty()) {
            return null;
        }
        Page<UserResponse> res = userService.searchUsers(key, pageable);

        return ApiResponse.<Page<UserResponse>>builder()
                .result(res)
                .build();
    }
}
