package com.eta.houzezbackend.service;

import com.eta.houzezbackend.dto.AgentGetDto;
import com.eta.houzezbackend.dto.AgentSignUpDto;
import com.eta.houzezbackend.exception.ResourceNotFoundException;
import com.eta.houzezbackend.mapper.AgentMapper;
import com.eta.houzezbackend.model.Agent;
import com.eta.houzezbackend.repository.AgentRepository;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public record AgentService(AgentRepository agentRepository,PasswordEncoder passwordEncoder ,AgentMapper agentMapper) {

    private static final String RESOURCE = "Agent";
    public AgentGetDto signUpNewAgent(AgentSignUpDto agentSignUpDto){

        Agent agent = new Agent();
        agent.setEmail(agentSignUpDto.getEmail());
        agent.setPassword(encryptPassword(agentSignUpDto.getPassword()));
        agent.setCompany(agentSignUpDto.getCompany());
        agent.setCompanyLogo(agentSignUpDto.getCompanyLogo());
        agent.setActiveLink(agentSignUpDto.getActiveLink());
        agent.setPhone(agentSignUpDto.getPhone());
        agent.setIfDelete(0);
        agent.setStatus("unactivated");

        long id = agentRepository.save(agent).getId();
        agent = setDefaultUserName(id);
        return agentMapper.agentToAgentGetDto(agent);
    }

    public AgentGetDto getAgent(Long id){
        return agentMapper.agentToAgentGetDto(find(id));
    }

    private String encryptPassword (String password){
        return passwordEncoder.encode(password);
    }
    private Agent setDefaultUserName(long id){
        Agent agent1 = find(id);
        agent1.setName("HOUZEZ_000000" + id);
        agentRepository.save(agent1);
        return agent1;
    }
    private Agent find(Long id) {
        return agentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException(RESOURCE, id));
    }

}
