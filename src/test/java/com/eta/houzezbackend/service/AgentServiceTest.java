package com.eta.houzezbackend.service;

import com.eta.houzezbackend.dto.AgentGetDto;
import com.eta.houzezbackend.dto.AgentSignUpDto;
import com.eta.houzezbackend.mapper.AgentMapper;
import com.eta.houzezbackend.model.Agent;
import com.eta.houzezbackend.repository.AgentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Date;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class AgentServiceTest {
    @Mock
    private AgentRepository agentRepository;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private AgentMapper agentMapper;


    @InjectMocks
    private AgentService agentService;

    private final AgentSignUpDto mockAgentSignUpDto = new AgentSignUpDto("test2@gmail.com",
            "123qqqqq.",
            "",
            "",
            "",
            "","");
    private final Agent mockAgent = new Agent(1L,"HOUSEZ0000" + 1L, "", "", "",
            "test2@gmail.com","123qqqqq.","",0,"",
            "unactivated", new Date(),new Date());

    @Test
    void shouldSaveNewAgentInAgentRepoWhenSignUpNewAgent() {

        Agent agent = new Agent(1L,"HOUSEZ0000" + 1L, "", "", "",
                "test2@gmail.com","123qqqqq.","",0,"",
                "unactivated", new Date(),new Date());
        when(agentRepository.save(agent)).thenReturn(mockAgent);

        when(agentRepository.save(agent).getId()).thenReturn(1L);
        agentService.signUpNewAgent(mockAgentSignUpDto);
        verify(agentRepository).save(mockAgent);

    }

    @Test
    void shouldGetAgentGetDtoWhenGetAgent() {

    }

}