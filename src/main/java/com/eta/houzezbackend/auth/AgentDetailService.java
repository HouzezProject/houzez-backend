package com.eta.houzezbackend.auth;

import com.eta.houzezbackend.model.Agent;
import com.eta.houzezbackend.repository.AgentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Slf4j
@Service
public record AgentDetailService(AgentRepository agentRepository) implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Agent agent = agentRepository.findByEmail(username).orElseThrow();

        return AgentDetail.builder()
                .username(agent.getEmail())
                .id(agent.getId())
                .password(agent.getPassword())
                .isAccountNonLocked(true)
                .isAccountNotExpired(true)
                .isCredentialsNonExpired(true)
                .isEnabled(true)
                .build();
    }
}
