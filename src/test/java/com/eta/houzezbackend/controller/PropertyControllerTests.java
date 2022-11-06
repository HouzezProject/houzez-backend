package com.eta.houzezbackend.controller;

import com.eta.houzezbackend.dto.AgentSignUpDto;
import com.eta.houzezbackend.dto.PropertyPostDto;
import com.eta.houzezbackend.mapper.AgentMapper;
import com.eta.houzezbackend.model.Agent;
import com.eta.houzezbackend.repository.AgentRepository;
import com.eta.houzezbackend.repository.PropertyRepository;
import com.eta.houzezbackend.util.PropertyType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class PropertyControllerTests extends ControllerIntTest {

    @Autowired
    private PropertyRepository propertyRepository;
    @Autowired
    private AgentController agentController;
    @Autowired
    private AgentMapper agentMapper;
    @Autowired
    private AgentRepository agentRepository;

    private long mockPropertyId;
    private long mockAgentId;
    private PropertyPostDto mockPropertyPostDto;

    @BeforeEach
    void addProperty() {
        propertyRepository.deleteAll();
        propertyRepository.flush();
        agentRepository.deleteAll();
        agentRepository.flush();
        Agent mockAgent = agentMapper.agentGetDtoToAgent(agentController.signUp(
                AgentSignUpDto
                        .builder()
                        .email("test3@gmail.com")
                        .password("123q¥@#aAq.")
                        .build()));
        mockAgentId = mockAgent.getId();
        mockAgent.setActivated(true);
        mockPropertyPostDto = PropertyPostDto.builder()
                .garage(1)
                .propertyType(PropertyType.HOUSE)
                .description("Mount house")
                .title("HOUSE with sea view")
                .preowned(false)
                .price(800000)
                .livingRoom(2)
                .bedroom(4)
                .bathroom(3)
                .landSize(200)
                .state("Tas")
                .suburb("Kingston")
                .postcode(7010)
                .build();
        mockPropertyId = agentController.addProperty(mockPropertyPostDto, mockAgentId).getId();
    }


    @Test
    void shouldGetPropertyInfo() throws Exception {
        mockMvc.perform(get("/properties/" + mockPropertyId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(mockPropertyId));
    }

    @Test
    void shouldGetPropertyInfoByAgent() throws Exception {
        mockMvc.perform(get("/agents/" + mockAgentId + "/properties"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].agent.id").value(mockAgentId));
    }

}
