package com.eta.houzezbackend.service;

import com.eta.houzezbackend.util.SystemParam;
import io.jsonwebtoken.*;
import io.jsonwebtoken.security.SignatureException;
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
        Claims claims = null;
        try {
            claims = Jwts.parserBuilder()
                    .setSigningKey(DatatypeConverter.parseBase64Binary(systemParam.getSECRET_KEY()))
                    .build()
                    .parseClaimsJws(jwt).getBody();
        } catch (ExpiredJwtException e) {
            throw new RuntimeException(e);
        } catch (UnsupportedJwtException e) {
            throw new RuntimeException(e);
        } catch (MalformedJwtException e) {
            throw new RuntimeException(e);
        } catch (SignatureException e) {
            throw new RuntimeException(e);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException(e);
        }
        return claims;
    }

}
