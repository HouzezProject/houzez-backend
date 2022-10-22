package com.eta.houzezbackend.filter;


import com.eta.houzezbackend.service.JwtService;
import io.jsonwebtoken.Claims;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RequiredArgsConstructor
public class JwtVerifyFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private static final String BEARER = "Bearer ";

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authKey = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (authKey == null || !authKey.startsWith(BEARER)) {
            filterChain.doFilter(request, response);
            return;
        }

        String token = authKey.replace(BEARER, "");

        Claims body = jwtService.getJwtBody(token);
        String username = body.getSubject();

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                username,
                null,
                null
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        filterChain.doFilter(request, response);

    }
}
