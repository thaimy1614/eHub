package com.datn.resource_service.service;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.datn.resource_service.dto.request.UploadResourceRequest;
import com.datn.resource_service.dto.response.PresignedUrlResponse;
import com.datn.resource_service.dto.response.ResourceResponse;
import com.datn.resource_service.exception.AppException;
import com.datn.resource_service.exception.ErrorCode;
import com.datn.resource_service.mapper.ResourceMapper;
import com.datn.resource_service.model.Resource;
import com.datn.resource_service.repository.ResourceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.Instant;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class ResourceServiceImpl implements ResourceService {
    private final ResourceRepository resourceRepository;
    private final AmazonS3 s3;
    private final ResourceMapper resourceMapper;

    @Value("${aws.s3.bucket.name}")
    private String bucketName;

    public PresignedUrlResponse generatePresignedUrl(String fileName, String fileType) {
        Date expiration = new Date(System.currentTimeMillis() + 1000 * 60 * 10);
        fileName = Instant.now().getEpochSecond() + fileName;
        GeneratePresignedUrlRequest request = new GeneratePresignedUrlRequest(bucketName, fileName)
                .withMethod(HttpMethod.PUT)
                .withContentType(fileType)
                .withExpiration(expiration);
        return PresignedUrlResponse.builder()
                .presignedUrl(s3.generatePresignedUrl(request).toString())
                .url(s3.getUrl(bucketName, fileName).toString())
                .build();
    }

    @Override
    public ResourceResponse uploadResource(UploadResourceRequest request) {
        Resource resource = resourceMapper.toResource(request);


        return resourceMapper.toResourceResponse(resource);
    }

    @Async
    public void upload(MultipartFile file, String id) {
        try {
            InputStream in = file.getInputStream();
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(file.getContentType());
            metadata.setContentLength(in.available());
            PutObjectRequest request = new PutObjectRequest(bucketName, id, file.getInputStream(), metadata);
            s3.putObject(request);
            String url = getUrl(id);
            Resource resource = resourceRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.RESOURCE_NOT_FOUND));
            resource.setUrl(url);
            resourceRepository.save(resource);
        } catch (IOException e) {
            resourceRepository.deleteById(id);
            throw new RuntimeException(e);
        }
    }

    public String getUrl(String objectName) {
        return s3.getUrl(bucketName, objectName).toString();
    }
}
