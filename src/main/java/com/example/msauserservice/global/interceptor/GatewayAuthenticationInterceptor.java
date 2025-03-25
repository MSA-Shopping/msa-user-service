package com.example.msauserservice.global.interceptor;

import com.example.msauserservice.global.security.GatewaySignatureVerifier;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;


@Component
public class GatewayAuthenticationInterceptor implements HandlerInterceptor {
    
    @Autowired
    private GatewaySignatureVerifier signatureVerifier;
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String userId = request.getHeader("X-User-Id");
        String timestamp = request.getHeader("X-Timestamp");
        String signature = request.getHeader("X-Signature");
        
        if (userId == null || timestamp == null || signature == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
        
        boolean isValid = signatureVerifier.isValidSignature(
            userId,
            Long.parseLong(timestamp),
            signature
        );
        
        if (!isValid) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }
        
        return true;
    }
} 