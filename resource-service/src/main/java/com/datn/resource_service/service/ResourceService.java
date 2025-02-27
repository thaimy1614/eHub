package com.datn.resource_service.service;

import com.datn.resource_service.dto.request.UploadResourceRequest;
import com.datn.resource_service.dto.response.PresignedUrlResponse;
import com.datn.resource_service.dto.response.ResourceResponse;

public interface ResourceService {
    ResourceResponse uploadResource(UploadResourceRequest request);

    PresignedUrlResponse generatePresignedUrl(String fileName, String fileType);
}
