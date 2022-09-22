package com.eta.houzezbackend.controller;

import com.eta.houzezbackend.dto.AgentSignUpDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
class AgentControllerTests {
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private AgentController agentController;

    private Long mockUserId;

    @BeforeEach
    void signUp() {
        mockUserId = agentController.signUp(new AgentSignUpDto("test1@gmail.com",
                "123qqqqq.",
                "",
                "company",
                "companyLogo",
                "phone","")).getId();
    }

    @Test
    void shouldReturn201AndAgentDtoWhenAgentIsCreated() throws Exception {
        AgentSignUpDto agentSignUpDto = new AgentSignUpDto("test2@gmail.com",
                "123qqqqq.",
                "",
                "company",
                "companyLogo",
                "phone","");

        mockMvc.perform(post("/Agents")
                        .content(objectMapper.writeValueAsString(agentSignUpDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value("test2@gmail.com"))
        ;
    }

    @Test
    void shouldReturn200AndAgentDtoWhenGetAgentDto() throws Exception {
        mockMvc.perform(get("/Agents/" + mockUserId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(mockUserId));
    }
}