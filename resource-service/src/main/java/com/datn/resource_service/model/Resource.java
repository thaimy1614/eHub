package com.datn.resource_service.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.Instant;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "resources")
@Builder
public class Resource {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", updatable = false, nullable = false)
    private String resourceId;

    @Column(name = "resource_name")
    private String resourceName;

    @Column(name = "url", nullable = false, columnDefinition = "TEXT")
    private String url;

    @Column(name = "type", nullable = false)
    private String type;

    @Column(name = "size", nullable = false)
    private Long size;

    @Column(name = "user_id")
    private String userId;

    @Column(name = "class_id")
    private String classId;

    @Enumerated(EnumType.STRING)
    @Column(name = "resource_type", nullable = false)
    private ResourceType resourceType;

    @Column(name = "uploaded_at", nullable = false, updatable = false)
    @CreationTimestamp
    private Instant uploadedAt;

    public enum ResourceType {
        LEARNING_MATERIAL,
        STORED_RESOURCE,
        PROFILE_PICTURE
    }
}
