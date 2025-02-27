package com.datn.user_service.mapper;

import com.datn.user_service.dto.response.UserResponse;
import com.datn.user_service.model.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponse toUserResponse(User user);
}
