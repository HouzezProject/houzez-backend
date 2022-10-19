package com.eta.houzezbackend;


import com.eta.houzezbackend.controller.AgentController;
import com.eta.houzezbackend.controller.ApplicationIntTest;
import com.eta.houzezbackend.dto.AgentSignUpDto;
import com.eta.houzezbackend.dto.UsernameAndPasswordAuthenticationRequest;
import com.eta.houzezbackend.repository.AgentRepository;
import com.eta.houzezbackend.service.AgentService;
import com.eta.houzezbackend.service.JwtService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class AuthTest extends ApplicationIntTest {

    @Autowired
    private JwtService jwtService;
    @Autowired
    private AgentService agentService;
    @Autowired
    private AgentRepository agentRepository;


    private UsernameAndPasswordAuthenticationRequest mockRequest;

    private Long mockId;

    private String jwtToken;

    @BeforeEach
    void signUp() {

        AuthenticationManager authenticationManager = authentication -> authentication;
        UsernamePasswordAuthenticationToken authRequest
                = new UsernamePasswordAuthenticationToken("test1@gmail.com", "123right.");
        Authentication authResult = authenticationManager.authenticate(authRequest);

        agentRepository.deleteAll();
        agentRepository.flush();
        AgentSignUpDto agentSignUpDto = AgentSignUpDto.builder().email("test1@gmail.com").password("123right.").build();
        agentService.signUpNewAgent(agentSignUpDto);

        jwtToken = "Bearer " + jwtService.createLoginJWT("1", 3600, authResult);

    }

    @Test
    void shouldReturn201AndAgentGetDtoWhenSignIn() throws Exception {
        mockRequest = UsernameAndPasswordAuthenticationRequest.builder().email("test1@gmail.com").password("123right.").build();

        mockMvc.perform(post("/agents/sign-in")
                        .content(objectMapper.writeValueAsString(mockRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value("test1@gmail.com"));
    }

    @Test
    void shouldReturn401WhenSignInButWrongPassword() throws Exception {
        mockRequest = UsernameAndPasswordAuthenticationRequest.builder().email("test1@gmail.com").password("123wrong.").build();

        mockMvc.perform(post("/agents/sign-in")
                        .content(objectMapper.writeValueAsString(mockRequest))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    void shouldReturn403WhenGetAgentButNoJwtToken() throws Exception {

        mockId = agentService.findByEmail("test1@gmail.com").getId();
        mockMvc.perform(get("/agents/" + mockId))
                .andExpect(status().isForbidden());
    }

    @Test
    void shouldReturn200AndAgentDtoWhenGetAgentAndHaveJwtToken() throws Exception {

        mockId = agentService.findByEmail("test1@gmail.com").getId();
        mockMvc.perform(get("/agents/" + mockId).header("Authorization", jwtToken))
                .andExpect(status().isOk());
    }

}

