package com.eta.houzezbackend.controller;

import com.eta.houzezbackend.dto.ImageGetDto;
import com.eta.houzezbackend.dto.PropertyGetDto;
import com.eta.houzezbackend.dto.PropertyPaginationGetDto;
import com.eta.houzezbackend.service.ImageService;
import com.eta.houzezbackend.service.PropertyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import java.util.List;

@RestController
@RequestMapping("properties")
@RequiredArgsConstructor
public class PropertyController {
    private final PropertyService propertyService;
    private final ImageService imageService;

    @GetMapping("/{id}")
    public PropertyGetDto getProperty(@PathVariable Long id) {
        return propertyService.getProperty(id);
    }

    @GetMapping("/{id}/images")
    public List<ImageGetDto> getImage(@PathVariable long id,
                                      @RequestParam(defaultValue = "0") int page,
                                     @Valid @RequestParam(defaultValue = "10") @Max(value = 50) int size) {
        return imageService.getImage(id, page, size);
    }

    @GetMapping
    public PropertyPaginationGetDto getAllProperty(@RequestParam(defaultValue = "0") int page,
                                                   @Valid @RequestParam(defaultValue = "10") @Max(value = 50) int size){
        return propertyService.getAllProperty(page,size);
    }

}
