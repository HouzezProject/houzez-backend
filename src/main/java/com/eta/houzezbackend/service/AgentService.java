package com.eta.houzezbackend.service;

import com.eta.houzezbackend.dto.AgentGetDto;
import com.eta.houzezbackend.dto.AgentSignUpDto;
import com.eta.houzezbackend.exception.ResourceNotFoundException;
import com.eta.houzezbackend.exception.UniqueEmailViolationException;
import com.eta.houzezbackend.mapper.AgentMapper;
import com.eta.houzezbackend.model.Agent;
import com.eta.houzezbackend.repository.AgentRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public record AgentService(AgentRepository agentRepository, PasswordEncoder passwordEncoder, AgentMapper agentMapper,
                           JwtService jwtService) {

    private static final String RESOURCE = "Agent";

    public AgentGetDto signUpNewAgent(AgentSignUpDto agentSignUpDto) {

        Agent agent = agentMapper.agentSignUpDtoToAgent(agentSignUpDto);
        agent.setPassword(passwordEncoder.encode(agentSignUpDto.getPassword()));
        try{
            agent = agentRepository.save(agent);
        }
        catch (DataIntegrityViolationException e) {
            if (e.getMostSpecificCause().getClass().getName().equals("org.postgresql.util.PSQLException"))
                throw new UniqueEmailViolationException(agent.getEmail());
            throw e;
        }

        return agentMapper.agentToAgentGetDto(agent);
    }

    public AgentGetDto getAgent(Long id) {
        return agentMapper.agentToAgentGetDto(find(id));
    }

    private Agent find(Long id) {
        return agentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(RESOURCE, id));
    }

    public String createSignUpLink(String baseUrl, String id, String name, int effectiveTimeInMinutes) {
        return baseUrl + "/agents/decode/" + jwtService().createJWT(id, name, effectiveTimeInMinutes);
    }

    public void findByEmail(String email) {
        agentRepository.findByEmail(email).orElseThrow(() -> new UniqueEmailViolationException(email));
    }
}
