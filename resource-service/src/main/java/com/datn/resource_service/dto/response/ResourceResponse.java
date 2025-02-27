package com.datn.resource_service.dto.response;

import com.datn.resource_service.model.Resource;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ResourceResponse {
    private String resourceId;
    private String resourceName;
    private String userId;
    private String classId;
    private String url;
    private Resource.ResourceType resourceType;
    private String type;
    private Instant uploadedAt;
    private Long size;
}
