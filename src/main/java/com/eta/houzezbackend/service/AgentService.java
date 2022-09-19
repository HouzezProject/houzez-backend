package com.eta.houzezbackend.service;

import com.eta.houzezbackend.dto.AgentDto;
import com.eta.houzezbackend.model.Agent;
import com.eta.houzezbackend.repository.AgentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AgentService {
    private final AgentRepository agentRepository;

    public long signUpNewAgent(AgentDto agentDto){
        Agent agent = new Agent();
        agent.setEmail(agentDto.getEmail());
        agent.setPassword(agentDto.getPassword());
        agent.setIfDelete(0);
        agent.setStatus("unactivated");
        long id = agentRepository.save(agent).getId();
        //agentRepository.findById(id);
        return id;
    }
}
