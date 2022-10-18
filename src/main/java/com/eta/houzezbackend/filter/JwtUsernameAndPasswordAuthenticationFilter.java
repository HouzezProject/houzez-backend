package com.eta.houzezbackend.filter;

import com.eta.houzezbackend.auth.AgentDetail;
import com.eta.houzezbackend.dto.UsernameAndPasswordAuthenticationRequest;
import com.eta.houzezbackend.service.JwtService;
import com.eta.houzezbackend.util.SystemParam;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JwtUsernameAndPasswordAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private static final String LOGIN_URL = "/agents/sign-in";
    private static final String BEARER = "Bearer ";
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final SystemParam systemParam;

    public JwtUsernameAndPasswordAuthenticationFilter(AuthenticationManager authenticationManager, JwtService jwtService, SystemParam systemParam) {

        super.setFilterProcessesUrl(LOGIN_URL);
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.systemParam = systemParam;
    }

    @Override
    @SneakyThrows
    public Authentication attemptAuthentication(HttpServletRequest request,
                                                HttpServletResponse response) throws AuthenticationException {
        UsernameAndPasswordAuthenticationRequest authenticationRequest = new ObjectMapper()
                .readValue(request.getInputStream(), UsernameAndPasswordAuthenticationRequest.class);

        Authentication authentication = new UsernamePasswordAuthenticationToken(
                authenticationRequest.getEmail(),
                authenticationRequest.getPassword()
        );

        return authenticationManager.authenticate(authentication);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain, Authentication authResult) throws ServletException, IOException {

        HttpServletRequestWrapper wrappedSavedRequest = new HttpServletRequestWrapper(request);
        String token = jwtService.createLoginJWT(((AgentDetail) authResult.getPrincipal()).getId().toString(), authResult.getName(), Integer.parseInt(systemParam.getSignInJwtExpiredMinute()), authResult);
        response.addHeader(HttpHeaders.AUTHORIZATION, BEARER + token);
        response.addHeader(HttpHeaders.ACCESS_CONTROL_EXPOSE_HEADERS, HttpHeaders.AUTHORIZATION);
        wrappedSavedRequest.setAttribute("username", ((AgentDetail) authResult.getPrincipal()).getUsername());
        chain.doFilter(wrappedSavedRequest, response);
    }
}
