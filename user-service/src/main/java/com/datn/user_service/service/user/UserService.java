package com.datn.user_service.service.user;

import com.datn.user_service.dto.request.RegisterUser;
import com.datn.user_service.dto.response.CountUserResponse;
import com.datn.user_service.dto.response.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    UserResponse getMyInfo(String userId);

    UserResponse updateMyInfo(String userId, RegisterUser userRequest);

    String getFullName(String userId);

    CountUserResponse countUsers();

    Page<UserResponse> searchUsers(String key, Pageable pageable);
}
