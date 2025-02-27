package com.datn.user_service.mapper;

import com.datn.user_service.dto.request.RegisterParentRequest;
import com.datn.user_service.dto.response.ParentResponse;
import com.datn.user_service.model.Parent;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ParentMapper {
    Parent toParent(RegisterParentRequest request);

    ParentResponse toParentResponse(Parent parent);
}
