package com.eta.houzezbackend.service;

import com.eta.houzezbackend.dto.AgentGetDto;
import com.eta.houzezbackend.dto.AgentSignUpDto;
import com.eta.houzezbackend.exception.EmailAddressException;
import com.eta.houzezbackend.exception.ResourceNotFoundException;
import com.eta.houzezbackend.exception.UniqueEmailViolationException;
import com.eta.houzezbackend.mapper.AgentMapper;
import com.eta.houzezbackend.model.Agent;
import com.eta.houzezbackend.repository.AgentRepository;
import com.eta.houzezbackend.util.SystemParam;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public record AgentService(AgentRepository agentRepository, PasswordEncoder passwordEncoder, AgentMapper agentMapper,
                           JwtService jwtService, EmailService emailService, SystemParam systemParam) {

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

        String registerLink = createSignUpLink(systemParam.getBASE_URL(),agent.getId().toString(),agent.getName(),10);

        try {
            emailService.sendEmail(agent.getEmail(), registerLink);
        } catch (Exception e) {
            throw new EmailAddressException();
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
        return baseUrl + "/verification?code=" + jwtService().createJWT(id, name, effectiveTimeInMinutes);
    }

    public Agent findByEmail(String email) {
        return agentRepository.findByEmail(email).orElseThrow(ResourceNotFoundException::new);
    }
}
