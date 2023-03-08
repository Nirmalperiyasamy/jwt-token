package com.example.jwt.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Service
@Slf4j
public class JwtUtil {

    @Value("${my.secret.string}")
    private String secret;

    public String generateToken(String username) {
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims, username);
    }

    private String createToken(Map<String, Object> claim, String username) {
        Date date = new Date(System.currentTimeMillis());
        return Jwts.builder()
                .setClaims(claim)
                .setSubject(username)
                .setIssuedAt(date)
                .setExpiration(addTime(date))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();
    }

    private Date addTime(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int seconds = 60;
        calendar.add(Calendar.SECOND, seconds);
        return calendar.getTime();
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        final String name = extractUsername(token);
        return (name.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
}
