package com.eta.houzezbackend.mapper;

import com.eta.houzezbackend.dto.AgentGetDto;
import com.eta.houzezbackend.dto.AgentSignUpDto;
import com.eta.houzezbackend.model.Agent;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.UUID;

@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE,imports = UUID.class)
public interface AgentMapper {
    AgentGetDto agentToAgentGetDto(Agent agent);

    @Mapping(target = "password", ignore = true)
    @Mapping(target = "name", expression = "java(UUID.randomUUID().toString())")
    @Mapping(target = "ifDelete", constant = "false")
    @Mapping(target = "status", constant = "false")
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdTime", ignore = true)
    @Mapping(target = "updatedTime", ignore = true)
    Agent agentSignUpDtoToAgent(AgentSignUpDto agentSignUpDto);
}
