package com.eta.houzezbackend.service;

import com.eta.houzezbackend.util.SystemParam;
import io.jsonwebtoken.*;

import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;


@Service
public record JwtService(SystemParam systemParam) {

    public String createJWT(String id, String name, Integer expDateInMinutes) {
        LocalDateTime dateTime = LocalDateTime.now().plus(Duration.of(expDateInMinutes, ChronoUnit.MINUTES));
        Date expDate = Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());

        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS384;

        JwtBuilder builder = Jwts.builder()
                .setId(id)
                .setSubject(name)
                .setExpiration(expDate)
                .signWith(Keys.hmacShaKeyFor(systemParam().getSecretKey().getBytes()), signatureAlgorithm);

        return builder.compact();
    }

    public String createLoginJWT(String id, int expDateInMinutes, Authentication authResult) {

        LocalDateTime dateTime = LocalDateTime.now().plus(Duration.of(expDateInMinutes, ChronoUnit.MINUTES));
        Date expDate = Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());

        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS384;

        JwtBuilder builder = Jwts.builder()
                .setSubject(authResult.getName())
                .claim("agent_id", id)
                .setIssuedAt(new Date())
                .setExpiration(expDate)
                .signWith(Keys.hmacShaKeyFor(systemParam().getSecretKey().getBytes()), signatureAlgorithm);


        return builder.compact();
    }


    public String createResetPasswordJWT(int expDateInMinutes, String email) {

        LocalDateTime dateTime = LocalDateTime.now().plus(Duration.of(expDateInMinutes, ChronoUnit.MINUTES));
        Date expDate = Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());

        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS384;

        JwtBuilder builder = Jwts.builder()
                .claim("email", email)
                .setIssuedAt(new Date())
                .setExpiration(expDate)
                .signWith(Keys.hmacShaKeyFor(systemParam().getSecretKey().getBytes()), signatureAlgorithm);
        return builder.compact();
    }

    public Claims getJwtBody(String jwt) {

        return Jwts.parserBuilder()
                .setSigningKey(Keys.hmacShaKeyFor(systemParam().getSecretKey().getBytes()))
                .build()
                .parseClaimsJws(jwt).getBody();

    }
}
