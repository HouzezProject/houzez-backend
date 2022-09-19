package com.eta.houzezbackend.controller;

import com.eta.houzezbackend.dto.AgentDto;
import com.eta.houzezbackend.service.AgentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;


@RestController
@RequestMapping("Agents")
@RequiredArgsConstructor
public class SignUpController {

    private final AgentService agentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public String signUp(@Valid @RequestBody AgentDto agentDto){
        long id = agentService.signUpNewAgent(agentDto);
        return "success, new agent's id is " + id;
    }
}
