package com.eta.houzezbackend.service;

import com.eta.houzezbackend.util.SystemParam;
import org.junit.jupiter.api.Test;

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class JwtServiceTest {

    @Mock
    SystemParam systemParam;

    @InjectMocks
    JwtService jwtService;

    @Test
    void shouldDecodeJwtWhenCreatedJwtAndDecodeJwt() {
        when(systemParam.getSecretKey()).thenReturn("5970337336763979244226452948404D6351665468576D5A7134743777217A25");
        String token = jwtService.createJWT("123", "name", 10);
        assertEquals("123", Objects.requireNonNull(jwtService.getJwtBody(token)).getId());
        assertEquals("name", Objects.requireNonNull(jwtService.getJwtBody(token)).getSubject());
    }

    @Test
    void shouldDecodeJwtWhenCreatedResetPasswordJwtAndDecodeJwt() {
        when(systemParam.getSecretKey()).thenReturn("5970337336763979244226452948404D6351665468576D5A7134743777217A25");
        String token = jwtService.createResetPasswordJWT(3600, "email");
        assertEquals("email", Objects.requireNonNull(jwtService.getJwtBody(token)).get("email"));
    }

}