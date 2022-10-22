package com.eta.houzezbackend.service;

import com.eta.houzezbackend.dto.AgentGetDto;
import com.eta.houzezbackend.dto.AgentSignUpDto;
import com.eta.houzezbackend.exception.*;
import com.eta.houzezbackend.mapper.AgentMapper;
import com.eta.houzezbackend.model.Agent;
import com.eta.houzezbackend.repository.AgentRepository;
import com.eta.houzezbackend.service.email.EmailService;
import com.eta.houzezbackend.util.SystemParam;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.stereotype.Service;


@Service
public record AgentService(AgentRepository agentRepository, AgentMapper agentMapper,
                           JwtService jwtService, EmailService emailService, SystemParam systemParam) {

    private static final String RESOURCE = "Agent";

    public AgentGetDto signUpNewAgent(AgentSignUpDto agentSignUpDto) {

        Agent agent = agentMapper.agentSignUpDtoToAgent(agentSignUpDto);

        agent.setPassword(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(agentSignUpDto.getPassword()));
        try {
            agent = agentRepository.save(agent);
        } catch (DataIntegrityViolationException e) {
            if (e.getMostSpecificCause().getClass().getName().equals("org.postgresql.util.PSQLException"))
                throw new UniqueEmailViolationException(agent.getEmail());
            throw e;
        }

        String registerLink = createSignUpLink(systemParam.getBaseUrl(), agent.getId().toString(), agent.getName(), 10);

        try {
            emailService.sendEmail(agent.getEmail(), registerLink);
        } catch (Exception e) {
            throw new EmailAddressException();
        }

        return agentMapper.agentToAgentGetDto(agent);
    }

    public AgentGetDto resetPassword(AgentSignUpDto agentSignUpDto) {

        Agent agent = agentRepository.findByEmail(agentSignUpDto.getEmail()).orElseThrow(() -> new ResourceNotFoundException(agentSignUpDto.getEmail()));
        agent.setPassword(PasswordEncoderFactories.createDelegatingPasswordEncoder().encode(agentSignUpDto.getPassword()));
        agent = agentRepository.save(agent);
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

    public String createResetPasswordLink(String baseUrl, String email, int effectiveTimeInMinutes) {
        return baseUrl + "/reset-password?code=" + jwtService().createResetPasswordJWT(effectiveTimeInMinutes, email);
    }

    public Agent findByEmail(String email) {
        return agentRepository.findByEmail(email).orElseThrow(ResourceNotFoundException::new);
    }

    public Agent setAgentToActive(String jwt) {
        Claims claims;
        try {
            claims = jwtService.getJwtBody(jwt);
        } catch (JwtException e) {
            throw new LinkExpiredException("Sign up");
        }
        Agent agent = find(Long.parseLong(claims.getId()));
        agent.setActivated(true);
        agentRepository.save(agent);
        return agent;
    }

    public AgentGetDto signIn(String username) {
        if (!findByEmail(username).getActivated())
            throw new AgentInactiveException();
        return agentMapper.agentToAgentGetDto(findByEmail(username));
    }
}
