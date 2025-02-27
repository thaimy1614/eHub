package com.datn.resource_service.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PresignedUrlResponse {
    private String presignedUrl;
    private String url;
}
