package com.datn.gateway.http;

import com.datn.gateway.dto.ApiResponse;
import com.datn.gateway.dto.request.IntrospectRequest;
import com.datn.gateway.dto.response.IntrospectResponse;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.PostExchange;
import reactor.core.publisher.Mono;

@Repository
public interface IdentityClient {
    @PostExchange(url = "/identity/introspect", contentType = MediaType.APPLICATION_JSON_VALUE)
    Mono<ApiResponse<IntrospectResponse>> introspect(@RequestBody IntrospectRequest introspectRequest);
}
