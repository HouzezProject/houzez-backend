package com.eta.houzezbackend.controller;

import com.eta.houzezbackend.dto.AgentSignUpDto;
import com.eta.houzezbackend.repository.AgentRepository;
import com.eta.houzezbackend.service.EmailService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.transaction.Transactional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class AgentControllerTests extends ApplicationIntTest{
    @Autowired
    private AgentController agentController;

    private Long mockUserId;

    private String mockUserEmail;

    @Autowired
    private AgentRepository agentRepository;

    @BeforeEach
    void signUp() {
        agentRepository.deleteAll();
        agentRepository.flush();
        mockUserId = agentController.signUp(AgentSignUpDto.builder().email("test3@gmail.com")
                .password("123qqqqq.").build()).getId();
        mockUserEmail = agentController.signUp(AgentSignUpDto.builder().email("agent002@gmail.com")
                .password("agent002@gmail.comA.").build()).getEmail();

    }

    @Test
    void shouldReturn201AndAgentDtoWhenAgentIsCreated() throws Exception {
        AgentSignUpDto agentSignUpDto = AgentSignUpDto.builder().email("test4@gmail.com").password("123qqqqq.").build();

        mockMvc.perform(post("/agents")
                        .content(objectMapper.writeValueAsString(agentSignUpDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.email").value("test4@gmail.com"));
    }

    @Test
    void shouldReturn200AndAgentDtoWhenGetAgentDto() throws Exception {
        mockMvc.perform(get("/agents/" + mockUserId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(mockUserId));
    }

    @Test
    void shouldReturn200WhenFindByEmail() throws Exception {
        mockMvc.perform(head("/agents?email=" + mockUserEmail))
                .andExpect(status().isOk());
    }

    @Test
    void shouldReturn404WhenCannotFindByEmail() throws Exception {
        mockMvc.perform(head("/agents?email=t" + mockUserEmail))
                .andExpect(status().isNotFound());
    }
}