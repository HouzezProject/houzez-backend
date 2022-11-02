package com.eta.houzezbackend.controller;


import com.eta.houzezbackend.dto.AgentSignUpDto;
import com.eta.houzezbackend.dto.PropertyCreateDto;
import com.eta.houzezbackend.mapper.AgentMapper;
import com.eta.houzezbackend.model.Agent;
import com.eta.houzezbackend.repository.AgentRepository;
import com.eta.houzezbackend.repository.PropertyRepository;
import com.eta.houzezbackend.util.Type;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

public class PropertyControllerTests extends ControllerIntTest {

    @Autowired
    private PropertyRepository propertyRepository;
    private long mockPropertyId;

    @Autowired
    private PropertyController propertyController;

    @Autowired
    private AgentController agentController;
    @Autowired
    private AgentMapper agentMapper;
    @Autowired
    private AgentRepository agentRepository;

    @BeforeEach
    void addProperty() {
        propertyRepository.deleteAll();
        propertyRepository.flush();
        agentRepository.deleteAll();
        agentRepository.flush();

        Agent mockAgent = agentMapper.agentGetDtoToAgent(agentController.signUp(AgentSignUpDto.builder().email("test3@gmail.com")
                .password("123q¥@#aAq.").build()));
        mockPropertyId = propertyController.addProperty(PropertyCreateDto.builder().propertyIsNew(true).description("Mount house").garage(1).outdoor("Swimming pool").indoor("Gym").latitude(2.12232323).longitude(23.2443343).status("new")
                .type(Type.HOUSE).title("HOUSE with sea view").price(800000).livingRoom(2).bedroom(4).bathroom(3).landSize(200).state("Tas").suburb("Kingston").postcode(7010).agent(mockAgent).build()).getId();

    }

    @Test
    void shouldReturn201AndPropertyCreateDtoWhenPropertyIsCreated() throws Exception {
        PropertyCreateDto propertyCreateDto = PropertyCreateDto.builder().type(Type.HOUSE).description("Mount house").title("HOUSE with sea view").price(800000).livingRoom(2).bedroom(4).bathroom(3).landSize(200).state("Tas").suburb("Kingston").postcode(7010).agent(Agent.builder().build()).build();
        mockMvc.perform(post("/properties")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(propertyCreateDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("HOUSE with sea view"));
    }

    @Test
    void shouldGetPropertyInfoWhenPropertyIsGot() throws Exception {
        mockMvc.perform(get("/properties/" + mockPropertyId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(mockPropertyId));

    }

}
