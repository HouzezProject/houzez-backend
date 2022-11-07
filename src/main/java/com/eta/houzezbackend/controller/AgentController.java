package com.eta.houzezbackend.controller;

import com.eta.houzezbackend.dto.*;
import com.eta.houzezbackend.model.Agent;
import com.eta.houzezbackend.service.AgentService;
import com.eta.houzezbackend.service.ImageService;
import com.eta.houzezbackend.service.PropertyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import java.util.Map;

@RestController
@RequestMapping("agents")
@RequiredArgsConstructor
public class AgentController {

    private final AgentService agentService;
    private final PropertyService propertyService;

    private final ImageService imageService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AgentGetDto signUp(@Valid @RequestBody AgentSignUpDto agentSignUpDto) {
        return agentService.signUpNewAgent(agentSignUpDto);
    }

    @PostMapping("/sign-in")
    public AgentGetDto signIn(@RequestAttribute String username) {
        return agentService.signIn(username);
    }

    @GetMapping("/{id}")
    public AgentGetDto getAgent(@PathVariable Long id) {
        return agentService.getAgent(id);
    }


    @PatchMapping("/{id}")
    public Agent activeAgent(@RequestParam String token, @PathVariable Long id) {
        return agentService.setAgentToActive(token);
    }

    @RequestMapping(method = {RequestMethod.HEAD})
    public void getAgentByEmail(@RequestParam String email) {
        agentService.findByEmail(email);
    }

    @PostMapping("/password")
    public void sendResetPasswordEmail(@RequestBody Map<String, String> map) {
        agentService.sendForgetPasswordEmail(map.get("email"));
    }

    @PostMapping("/email")
    public void resendEmail(@RequestBody Map<String, String> map) {
        agentService.resendEmail(map.get("email"));
    }

    @PostMapping("/{id}/properties")
    @ResponseStatus(HttpStatus.CREATED)
    public PropertyGetDto addProperty(@Valid @RequestBody PropertyPostDto propertyCreateDto, @PathVariable long id) {
        return propertyService.createNewProperty(propertyCreateDto, id);
    }

    @PostMapping("/{agent_id}/properties/{property_id}/images")
    @ResponseStatus(HttpStatus.CREATED)
    public ImageGetDto addImage(@Valid @RequestBody ImagePostDto imagePostDto, @PathVariable long agent_id, @PathVariable long property_id) {
        return imageService.addImage(imagePostDto, agent_id, property_id);
    }

    @GetMapping("/{id}/properties")
    public PropertyPaginationGetDto getPropertiesByAgent(@PathVariable long id, @RequestParam(defaultValue = "0") int page,
                                                         @RequestParam(defaultValue = "10") @Max(value = 50) int size) {
        return propertyService.getPropertiesByAgent(id, page, size);
    }
}
