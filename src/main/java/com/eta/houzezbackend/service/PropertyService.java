package com.eta.houzezbackend.service;

import com.eta.houzezbackend.dto.PropertyPostDto;
import com.eta.houzezbackend.dto.PropertyGetDto;
import com.eta.houzezbackend.exception.ResourceNotFoundException;
import com.eta.houzezbackend.mapper.PropertyMapper;
import com.eta.houzezbackend.model.Agent;
import com.eta.houzezbackend.model.Property;
import com.eta.houzezbackend.repository.PropertyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PropertyService {

    private final PropertyRepository propertyRepository;
    private final PropertyMapper propertyMapper;
    private final AgentService agentService;


    public PropertyGetDto createNewProperty(PropertyPostDto propertyCreateDto, long agentId) {
        Property property = propertyMapper.propertyCreateDtoToProperty(propertyCreateDto);
        Agent agent = agentService.find(agentId);
        property.setAgent(agent);
        propertyRepository.save(property);
        return propertyMapper.propertyToPropertyGetDto(property);
    }

    @Transactional(readOnly = true)
    public PropertyGetDto getProperty(Long id) {
        Property property = propertyRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Property", id));
        return propertyMapper.propertyToPropertyGetDto(property);
    }

    @Transactional
    public List<PropertyGetDto> getPropertiesByAgent(long id, int page, int size) {
        Pageable paging = PageRequest.of(page, size);
        Page<Property> properties = propertyRepository.findByAgent_Id(id, paging);
        return properties.getContent().stream()
                .map(propertyMapper::propertyToPropertyGetDto)
                .collect(Collectors.toList());
    }
}
