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
    public PropertyPaginationGetDto getPropertyByCriteria(@RequestParam(defaultValue = "0") int page,
                                                          @Valid @RequestParam(defaultValue = "10") @Max(value = 50) int size,
                                                          @RequestParam(defaultValue = "0") int filter_livingroom,
                                                          @RequestParam(defaultValue = "0") int filter_bedroom,
                                                          @RequestParam(defaultValue = "0") int filter_bathroom,
                                                          @RequestParam(defaultValue = "0") int filter_garage,
                                                          @RequestParam(defaultValue = "-10") double ne_lat,
                                                          @RequestParam(defaultValue = "154") double ne_lng,
                                                          @RequestParam(defaultValue = "-43") double sw_lat,
                                                          @RequestParam(defaultValue = "112") double sw_lng,
                                                          @RequestParam(defaultValue = "0") int filter_postcode,
                                                          @RequestParam(defaultValue = "0") int filter_min_price,
                                                          @RequestParam(defaultValue = "100000000") int filter_max_price,
                                                          @RequestParam(defaultValue = "") String filter_property_type) {
        return propertyService.getPropertyByCriteria(
                page,
                size,
                filter_livingroom,
                filter_bedroom,
                filter_bathroom,
                filter_garage,
                ne_lat,
                ne_lng,
                sw_lat,
                sw_lng,
                filter_postcode,
                filter_min_price,
                filter_max_price,
                filter_property_type
        );
    }

}
