package com.datn.gateway.configuration;

import com.datn.gateway.dto.ApiResponse;
import com.datn.gateway.dto.response.IntrospectResponse;
import com.datn.gateway.service.IdentityService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Slf4j
public class AuthenticationFilter implements GlobalFilter, Ordered {
    private final IdentityService identityService;
    private final ObjectMapper mapper;
    String[] POST_PUBLIC_ENDPOINTS = {
            "/**/swagger-ui.html",
            "/api/user/identity/introspect",
            "/api/user/identity/login",
            "/api/user/identity/forget-password/get-otp",
            "/api/user/identity/forget-password/check-otp",
            "/api/**"
    };

    String[] GET_PUBLIC_ENDPOINTS = {
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/api/**/swagger/**/swagger.json",
            "/api/**/swagger-ui.html",
            "/api/**/v3/api-docs",
            "/api/**/v3/api-docs/**",
            "/api/user/identity/verify",
            "/api/**",
    };

    @Value("${app.api-prefix}")
    private String prefix;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        log.info("{}", isPublicEndpoint(exchange.getRequest()));
        log.info(exchange.getRequest().getPath().toString());
        if (isPublicEndpoint(exchange.getRequest())) {
            return chain.filter(exchange);
        }
        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            try {
                return unauthenticated(exchange.getResponse());
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }
        String token = authHeader.replace("Bearer ", "");
        log.info(token);
        return identityService.introspect(token).flatMap(responseObjectResponseEntity -> {
            log.info(String.valueOf(responseObjectResponseEntity.getResult().isValid()));
            if (responseObjectResponseEntity.getResult().isValid()) {
                return chain.filter(exchange);
            } else {
                try {
                    return unauthenticated(exchange.getResponse());
                } catch (JsonProcessingException e) {
                    return Mono.error(new RuntimeException(e));
                }
            }
        }).onErrorResume(throwable -> {
            try {
                return unauthenticated(exchange.getResponse());
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });

    }

    @Override
    public int getOrder() {
        return -1;
    }

    private boolean isPublicEndpoint(ServerHttpRequest request) {
        String path = request.getURI().getPath();
        String method = request.getMethod().name();

        AntPathMatcher pathMatcher = new AntPathMatcher();

        boolean isPostPublic = Arrays.stream(POST_PUBLIC_ENDPOINTS)
                .anyMatch(s -> pathMatcher.match(s, path) && "POST".equalsIgnoreCase(method));

        boolean isGetPublic = Arrays.stream(GET_PUBLIC_ENDPOINTS)
                .anyMatch(s -> pathMatcher.match(s, path) && "GET".equalsIgnoreCase(method));

        return isPostPublic || isGetPublic;
    }



    Mono<Void> unauthenticated(ServerHttpResponse response) throws JsonProcessingException {
        ApiResponse<IntrospectResponse> responseObject = new ApiResponse<>();
        responseObject.setCode(8888);
        responseObject.setMessage("Invalid token");
        responseObject.setResult(IntrospectResponse.builder().valid(false).build());
        String body = mapper.writeValueAsString(responseObject);
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        response.getHeaders().add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        return response.writeWith(Mono.just(response.bufferFactory().wrap(body.getBytes())));
    }
}
