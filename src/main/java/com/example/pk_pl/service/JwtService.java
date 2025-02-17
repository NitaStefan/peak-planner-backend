package com.example.pk_pl.service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.util.Date;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${application.security.jwt.secret-key}")
    private String secretKey;

    @Value("${application.security.jwt.access-token-exp}")
    private long accessTokenExp;

    @Value("${application.security.jwt.refresh-token-exp}")
    private long refreshTokenExp;

    public String generateAccessToken(String username) {

        return generateToken(username, accessTokenExp);
    }

    public String generateRefreshToken(String username) {

        return generateToken(username, refreshTokenExp);
    }

    private String generateToken(String username, long expireTime) {

        return Jwts.builder()
                .subject(username)
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + expireTime))
                .signWith(getSigninKey())
                .compact();
    }

    private SecretKey getSigninKey() {

        byte[] keyBytes = Decoders.BASE64.decode(secretKey);

        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractUsername(String token) {

        return extractClaim(token, Claims::getSubject);
    }

    // check if the extracted username is the same as the authenticated user's
    public boolean isValid(String token, UserDetails user) {

        String username = extractUsername(token);

        return username.equals(user.getUsername()) && !isTokenExpired(token);
    }

    private <T> T extractClaim(String token, Function<Claims, T> claimResolver) {
        final Claims claims = extractAllClaims(token);
        return claimResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser()
                .verifyWith(getSigninKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }
}
