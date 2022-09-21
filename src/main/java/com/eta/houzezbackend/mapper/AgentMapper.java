package com.eta.houzezbackend.mapper;

import com.eta.houzezbackend.dto.AgentGetDto;
import com.eta.houzezbackend.dto.AgentSignUpDto;
import com.eta.houzezbackend.model.Agent;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;


@Mapper(componentModel = "spring", unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface AgentMapper {
    AgentGetDto agentToAgentGetDto(Agent agent);

    //Agent agentSignUpDtoToAgent(AgentSignUpDto agentSignUpDto);
}
