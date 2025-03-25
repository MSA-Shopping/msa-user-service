package com.example.msauserservice.global.security;

import org.springframework.stereotype.Component;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Component
public class GatewaySignatureVerifier {

    private String secretKey = "7c15afabb9fdb21d396dcb9510aa64504d09a627a99772cae3ba77440f4de7d9";
    
    private static final String HMAC_ALGORITHM = "HmacSHA256";
    private static final long MAX_TIMESTAMP_DIFF = 300000; // 5 minutes in milliseconds
    
    public boolean isValidSignature(String userId, long timestamp, String receivedSignature) {
        // 타임스탬프 검증
        long currentTime = System.currentTimeMillis();
        if (Math.abs(currentTime - timestamp) > MAX_TIMESTAMP_DIFF) {
            return false;
        }
        
        // 서명 검증
        String expectedSignature = createSignature(userId, timestamp);
        return expectedSignature.equals(receivedSignature);
    }
    
    private String createSignature(String userId, long timestamp) {
        String message = userId + ":" + timestamp;
        try {
            Mac mac = Mac.getInstance(HMAC_ALGORITHM);
            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKey.getBytes(StandardCharsets.UTF_8), HMAC_ALGORITHM);
            mac.init(secretKeySpec);
            byte[] hash = mac.doFinal(message.getBytes(StandardCharsets.UTF_8));
            return Base64.getEncoder().encodeToString(hash);
        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException("Failed to create signature", e);
        }
    }
} 