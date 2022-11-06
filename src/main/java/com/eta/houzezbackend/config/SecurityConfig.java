package com.eta.houzezbackend.config;

import com.eta.houzezbackend.auth.AgentDetailService;
import com.eta.houzezbackend.auth.JwtVerifyEntryPoint;
import com.eta.houzezbackend.filter.JwtUsernameAndPasswordAuthenticationFilter;
import com.eta.houzezbackend.filter.JwtVerifyFilter;
import com.eta.houzezbackend.service.JwtService;
import com.eta.houzezbackend.util.SystemParam;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

import java.util.List;

@Setter
@ConfigurationProperties(prefix = "cors")
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private static final String[] AUTH_URL_WHITELIST = {
            "/agents",
            "/actuator/**",
            "/agents/sign-in",
            "/properties/**",
            "/agents/forget-password",
            "/agents/resend-email",
            "/agents/reset-password"
    };
    private final AgentDetailService agentDetailService;
    private final JwtService jwtService;
    private final SystemParam systemParam;

    private List<String> allowedOrigins;
    private List<String> allowedMethods;
    private List<String> allowedHeaders;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf().disable()
                .cors().configurationSource(request -> {
                    var cors = new CorsConfiguration();
                    cors.setAllowedOrigins(allowedOrigins);
                    cors.setAllowedMethods(allowedMethods);
                    cors.setAllowedHeaders(allowedHeaders);
                    return cors;
                })
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests(authorize ->
                        authorize.antMatchers(AUTH_URL_WHITELIST).permitAll()
                                .anyRequest().authenticated()
                )
                .addFilter(
                        new JwtUsernameAndPasswordAuthenticationFilter(authenticationManager(),
                                jwtService,
                                systemParam))
                .addFilterAfter(new JwtVerifyFilter(jwtService), JwtUsernameAndPasswordAuthenticationFilter.class)

                .exceptionHandling()
                .authenticationEntryPoint(
                        new JwtVerifyEntryPoint()
                )
                .and().build();
    }

    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(PasswordEncoderFactories.createDelegatingPasswordEncoder());
        provider.setUserDetailsService(agentDetailService);
        return new ProviderManager(provider);
    }
}
