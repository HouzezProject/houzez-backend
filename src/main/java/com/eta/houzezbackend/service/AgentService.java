package com.eta.houzezbackend.service;

import com.eta.houzezbackend.dto.AgentSignUpDto;
import com.eta.houzezbackend.model.Agent;
import com.eta.houzezbackend.repository.AgentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AgentService {
    private final AgentRepository agentRepository;

    @Autowired
    private final PasswordEncoder passwordEncoder;




    public long signUpNewAgent(AgentSignUpDto agentSignUpDto){
        Agent agent = new Agent();

        agent.setEmail(agentSignUpDto.getEmail());
        agent.setPassword(encryptPassword(agentSignUpDto.getPassword()));
        agent.setIfDelete(0);
        agent.setStatus("unactivated");

        long id = agentRepository.save(agent).getId();

        agentRepository.findById(id).ifPresent(agent1 -> {
            agent1.setName("HOUZEZ" + id);
            agentRepository.save(agent1);
        });

        return id;
    }

    private String encryptPassword (String password){
        return passwordEncoder.encode(password);
    }

}
