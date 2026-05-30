package com.music.server.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Instant;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Service
public class AdminAuthService {

    @Value("${admin.username}")
    private String adminUsername;

    @Value("${admin.password}")
    private String adminPassword;

    private final Map<String, Long> tokenStore = new HashMap<>();

    private static final long TOKEN_EXPIRE_TIME = 24 * 60 * 60 * 1000;

    public String login(String username, String password) {
        if (!adminUsername.equals(username) || !adminPassword.equals(password)) {
            return null;
        }

        String token = generateToken(username);
        tokenStore.put(token, Instant.now().toEpochMilli());
        return token;
    }

    public boolean validateToken(String token) {
        if (token == null || token.isEmpty()) {
            return false;
        }

        Long createTime = tokenStore.get(token);
        if (createTime == null) {
            return false;
        }

        long currentTime = Instant.now().toEpochMilli();
        return (currentTime - createTime) < TOKEN_EXPIRE_TIME;
    }

    public void logout() {
        tokenStore.clear();
    }

    private String generateToken(String username) {
        try {
            String raw = username + UUID.randomUUID().toString() + Instant.now().toEpochMilli();
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(raw.getBytes());
            return Base64.getUrlEncoder().withoutPadding().encodeToString(hash);
        } catch (NoSuchAlgorithmException e) {
            return UUID.randomUUID().toString();
        }
    }
}
