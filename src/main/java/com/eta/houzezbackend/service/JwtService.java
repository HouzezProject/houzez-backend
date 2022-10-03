package com.eta.houzezbackend.service;

import com.eta.houzezbackend.util.SystemParam;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Service;

import javax.xml.bind.DatatypeConverter;
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

        byte[] apiKeySecretBytes = DatatypeConverter.parseBase64Binary(systemParam.getSECRET_KEY());

        JwtBuilder builder = Jwts.builder()
                .setId(id)
                .setSubject(name)
                .setExpiration(expDate)
                .signWith(signatureAlgorithm, apiKeySecretBytes);

        return builder.compact();
    }

    public Claims getJwtBody(String jwt) {

            return Jwts.parserBuilder()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(systemParam.getSECRET_KEY()))
                    .build()
                    .parseClaimsJws(jwt).getBody();
    }

}
