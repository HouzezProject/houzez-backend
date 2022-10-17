package com.eta.houzezbackend.service;

import com.eta.houzezbackend.util.SystemParam;
import io.jsonwebtoken.*;

import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;
import java.time.Duration;
import java.time.LocalDate;
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

        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(systemParam.getSecretKey());

        JwtBuilder builder = Jwts.builder()
                .setId(id)
                .setSubject(name)
                .setExpiration(expDate)
                .signWith(Keys.hmacShaKeyFor(systemParam().getSecretKey().getBytes()), signatureAlgorithm);

        return builder.compact();
    }

    public String createLoginJWT(String id, String name, Integer expDateInMinutes, Authentication authResult) {
        LocalDateTime dateTime = LocalDateTime.now().plus(Duration.of(expDateInMinutes, ChronoUnit.MINUTES));
        Date expDate = Date.from(dateTime.atZone(ZoneId.systemDefault()).toInstant());

        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS384;

        JwtBuilder builder = Jwts.builder()
                .setSubject(authResult.getName())
                .claim("authorities", authResult.getAuthorities())
                .claim("agent_id", id)
                .setIssuedAt(new Date())
                .setExpiration(java.sql.Date.valueOf(LocalDate.now().plusDays(1)))
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
