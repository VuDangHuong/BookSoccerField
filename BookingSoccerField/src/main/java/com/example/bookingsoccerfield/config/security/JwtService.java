package com.example.bookingsoccerfield.config.security;

import com.example.bookingsoccerfield.models.entity.User;
import com.example.bookingsoccerfield.repository.UserRepository;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
@Service
public class JwtService {
    // Dán key bạn đã sinh ở đây
    private static final String BASE64_SECRET = "B3ipHddH7v8TgHjEglILuvm8Hsybx+1bCNMpHHgNdUc=";

    private final Key key = Keys.hmacShaKeyFor(Base64.getDecoder().decode(BASE64_SECRET));
    private  final UserRepository userRepository;

    public JwtService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String generateToken(String email) {
        User user = userRepository.findByEmail(email).orElseThrow();
        return Jwts.builder()
                .setSubject(email)
                .claim("role", user.getRole().name()) // ✅ thêm role vào claim
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 1 day
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }


    public String extractEmail(String token) {
        return Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token).getBody().getSubject();
    }

    public boolean isTokenValid(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            return false;
        }
    }
}
