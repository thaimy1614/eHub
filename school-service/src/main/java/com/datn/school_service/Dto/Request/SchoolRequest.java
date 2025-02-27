package com.datn.school_service.Dto.Request;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SchoolRequest {

    @Column(name = "SchoolName", length = 200)
    private String schoolName;

    @Column(name = "SchoolAddress", length = 500)
    private String schoolAddress;

    @Column(name = "PrincipalUserID", length = 50)
    private String principalUserID;

    @Column(name = "VicePrincipalUserID", length = 50)
    private String vicePrincipalUserID;
}
