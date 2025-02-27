package com.datn.resource_service.mapper;

import com.datn.resource_service.dto.request.UploadResourceRequest;
import com.datn.resource_service.dto.response.ResourceResponse;
import com.datn.resource_service.model.Resource;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ResourceMapper {
    Resource toResource(UploadResourceRequest request);

    ResourceResponse toResourceResponse(Resource resource);
}
