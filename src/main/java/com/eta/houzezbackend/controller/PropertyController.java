package com.eta.houzezbackend.controller;

import com.eta.houzezbackend.dto.PropertyGetDto;
import com.eta.houzezbackend.service.PropertyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("properties")
@RequiredArgsConstructor
public class PropertyController {
    private final PropertyService propertyService;

    @GetMapping("/{id}")
    public PropertyGetDto getProperty(@PathVariable Long id) {
        return propertyService.getProperty(id);
    }

}
