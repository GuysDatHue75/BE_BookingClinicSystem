package com.example.bookingclinic.user.service;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.HashMap;
import java.util.Map;

@Service
public class StringeeService {

    // Thay bằng SID và Secret lấy từ Stringee Dashboard
    @Value("${stringee.app.key}")
    private String stringeeSid;

    @Value("${stringee.app.secret}")
    private String stringeeSecret;

    public String generateToken(String userId) {
        long nowMillis = System.currentTimeMillis();
        // Stringee yêu cầu 'exp' tính bằng GIÂY (Unix Timestamp)
        long expSeconds = (nowMillis / 1000) + 3600; 

        Map<String, Object> header = new HashMap<>();
        header.put("typ", "JWT");
        header.put("alg", "HS256");
        header.put("cty", "stringee-api;v=1");

        // Tạo khóa an toàn từ Secret Key
        Key signingKey = Keys.hmacShaKeyFor(stringeeSecret.getBytes(StandardCharsets.UTF_8));

        return Jwts.builder()
                .setHeader(header)
                .claim("jti", stringeeSid + "-" + nowMillis)
                .claim("iss", stringeeSid)
                .claim("exp", expSeconds) // Gửi dạng Long (giây)
                .claim("userId", userId)
                .signWith(signingKey, SignatureAlgorithm.HS256) // Cách ký mới của JJWT 0.11+
                .compact();
    }
}