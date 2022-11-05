package com.eta.houzezbackend.controller;

import com.eta.houzezbackend.dto.AgentSignUpDto;
import com.eta.houzezbackend.dto.PropertyCreateDto;
import com.eta.houzezbackend.dto.PropertyGetDto;
import com.eta.houzezbackend.mapper.AgentMapper;
import com.eta.houzezbackend.model.Agent;
import com.eta.houzezbackend.repository.AgentRepository;
import com.eta.houzezbackend.repository.PropertyRepository;
import com.eta.houzezbackend.util.PropertyType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class PropertyControllerTests extends ControllerIntTest {

    @Autowired
    private PropertyRepository propertyRepository;
    private long mockPropertyId;
    @Autowired
    private AgentController agentController;

    @Autowired
    private AgentMapper agentMapper;

    @Autowired
    private PropertyController propertyController;

    @Autowired
    private AgentRepository agentRepository;
    private PropertyCreateDto mockPropertyCreateDto;
    private PropertyGetDto mockPropertyGetDto;

    @BeforeEach
    void addProperty() {
        propertyRepository.deleteAll();
        propertyRepository.flush();
        agentRepository.deleteAll();
        agentRepository.flush();
        Agent mockAgent = agentMapper.agentGetDtoToAgent(agentController.signUp(AgentSignUpDto.builder().email("test3@gmail.com")
                .password("123q¥@#aAq.").build()));
        long mockAgentId = mockAgent.getId();
        mockAgent.setActivated(true);
        mockPropertyCreateDto = PropertyCreateDto.builder().garage(1).propertyType(PropertyType.HOUSE).description("Mount house").title("HOUSE with sea view").propertyIsNew(true).price(800000).livingRoom(2).bedroom(4).bathroom(3).landSize(200).state("Tas").suburb("Kingston").postcode(7010).build();
        mockPropertyId = agentController.addProperty(mockPropertyCreateDto, mockAgentId).getId();
    }


    @Test
    void shouldGetPropertyInfoWhenPropertyIsGot() throws Exception {
        mockMvc.perform(get("/properties/" + mockPropertyId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(mockPropertyId));
    }

}
