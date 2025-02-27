package com.datn.resource_service.dto.request;

import com.datn.resource_service.model.Resource;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UploadResourceRequest {
    private String resourceName;
    private Resource.ResourceType resourceType;
    private String userId;
    private String classId;
    private String url;
    private Long size;
    private String type;
}
