package com.datn.resource_service.controller;

import com.datn.resource_service.dto.ApiResponse;
import com.datn.resource_service.dto.request.UploadResourceRequest;
import com.datn.resource_service.dto.response.PresignedUrlResponse;
import com.datn.resource_service.dto.response.ResourceResponse;
import com.datn.resource_service.service.ResourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("${application.api.prefix}")
public class ResourceController {
    private final ResourceService resourceService;

    @PostMapping("/upload")
    ApiResponse<ResourceResponse> uploadResource(
            @RequestBody UploadResourceRequest uploadResourceRequest
    ) {
        ResourceResponse resourceResponse = resourceService.uploadResource(uploadResourceRequest);
        return ApiResponse.<ResourceResponse>builder()
                .result(resourceResponse)
                .build();
    }

    @GetMapping("/presigned-url")
    ApiResponse<PresignedUrlResponse> getPresignedUrl(
            @RequestParam String fileName,
            @RequestParam String fileType
    ) {
        PresignedUrlResponse response = resourceService.generatePresignedUrl(fileName, fileType);
        return ApiResponse.<PresignedUrlResponse>builder()
                .result(response)
                .build();
    }

}
