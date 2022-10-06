package com.eta.houzezbackend.repository;

import com.eta.houzezbackend.model.Agent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AgentRepository extends JpaRepository<Agent, Long> {
    Optional<Agent> findByEmail(String email);
}
