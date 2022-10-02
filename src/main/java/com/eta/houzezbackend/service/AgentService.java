package com.eta.houzezbackend.service;

import com.eta.houzezbackend.dto.AgentGetDto;
import com.eta.houzezbackend.dto.AgentSignUpDto;
import com.eta.houzezbackend.dto.DuplicateEmailCheckDto;
import com.eta.houzezbackend.exception.ResourceNotFoundException;
import com.eta.houzezbackend.mapper.AgentMapper;
import com.eta.houzezbackend.model.Agent;
import com.eta.houzezbackend.repository.AgentRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public record AgentService(AgentRepository agentRepository, PasswordEncoder passwordEncoder, AgentMapper agentMapper) {

    private static final String RESOURCE = "Agent";

    public AgentGetDto signUpNewAgent(AgentSignUpDto agentSignUpDto) {

        Agent agent = agentMapper.agentSignUpDtoToAgent(agentSignUpDto);
        agent.setPassword(passwordEncoder.encode(agentSignUpDto.getPassword()));
        agent = agentRepository.save(agent);
        return agentMapper.agentToAgentGetDto(agent);
    }

    public AgentGetDto getAgent(Long id) {
        return agentMapper.agentToAgentGetDto(find(id));
    }

    public DuplicateEmailCheckDto getAgentByEmail(String email) {
        DuplicateEmailCheckDto duplicateEmailCheckDto = agentMapper.DuplicateEmailCheckResultDto(findByEmail(email));
        duplicateEmailCheckDto.setDuplicateEmailCheckResultDto(true);
        return duplicateEmailCheckDto;
    }

    private Agent find(Long id) {
        return agentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(RESOURCE, id));
    }

    private Agent findByEmail(String email) {
        return agentRepository.findByEmail(email).orElseThrow(() -> new ResourceNotFoundException(RESOURCE, email));
    }

}
