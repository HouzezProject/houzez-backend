package com.eta.houzezbackend.repository;

import com.eta.houzezbackend.model.Agent;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgentRepository extends JpaRepository<Agent, Long> {
}
