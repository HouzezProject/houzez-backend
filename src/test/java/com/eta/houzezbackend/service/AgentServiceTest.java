package com.eta.houzezbackend.service;

import com.eta.houzezbackend.dto.AgentGetDto;
import com.eta.houzezbackend.dto.AgentSignUpDto;
import com.eta.houzezbackend.mapper.AgentMapper;
import com.eta.houzezbackend.model.Agent;
import com.eta.houzezbackend.repository.AgentRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Date;
import java.util.Optional;
import java.util.UUID;

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


    private final AgentSignUpDto mockAgentSignUpDto = AgentSignUpDto
            .builder()
            .email("test2@gmail.com")
            .password("123qqqqq.")
            .build();
    private final AgentGetDto mockAgentGetDto = AgentGetDto
            .builder().id(1L)
            .name("name")
            .email("test2@gmail.com")
            .activated(false)
            .deleted(false)
            .createdTime(new Date())
            .updatedTime(new Date())
            .build();

    private final Agent mockAgent = Agent.builder()
            .id(1L)
            .name("name")
            .email("test2@gmail.com")
            .password("123qqqqq.")
            .activated(false)
            .deleted(false)
            .createdTime(new Date())
            .updatedTime(new Date())
            .build();

    @Test
    void shouldSaveNewAgentInAgentRepoWhenSignUpNewAgent() {

        String mockPsd = "123qqqqq.";

        when(agentMapper.agentSignUpDtoToAgent(mockAgentSignUpDto)).thenReturn(mockAgent);
        when(passwordEncoder.encode(mockPsd)).thenReturn("123qqqqq.");
        when(agentRepository.save(mockAgent)).thenReturn(mockAgent);
        when(agentMapper.agentToAgentGetDto(mockAgent)).thenReturn(mockAgentGetDto);

        agentService.signUpNewAgent(mockAgentSignUpDto);
        assertEquals(agentService.signUpNewAgent(mockAgentSignUpDto).getId(), mockAgentGetDto.getId());
    }

    @Test
    void shouldGetAgentGetDtoWhenGetAgent() {
        Long mockId = 1L;
        when(agentMapper.agentToAgentGetDto(mockAgent)).thenReturn(mockAgentGetDto);
        when(agentRepository.findById(mockId)).thenReturn(Optional.of(mockAgent));
        assertEquals(mockAgentGetDto,agentService.getAgent(mockId));
    }

}