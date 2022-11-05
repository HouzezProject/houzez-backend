package com.eta.houzezbackend.service;

import com.eta.houzezbackend.dto.PropertyCreateDto;
import com.eta.houzezbackend.dto.PropertyGetDto;
import com.eta.houzezbackend.exception.ResourceNotFoundException;
import com.eta.houzezbackend.mapper.PropertyMapper;
import com.eta.houzezbackend.model.Agent;
import com.eta.houzezbackend.model.Property;
import com.eta.houzezbackend.repository.AgentRepository;
import com.eta.houzezbackend.repository.PropertyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PropertyService {

    private final PropertyRepository propertyRepository;
    private final PropertyMapper propertyMapper;
    private final AgentRepository agentRepository;

    public PropertyGetDto createNewProperty(PropertyCreateDto propertyCreateDto, long agentId) {
        Property property = propertyMapper.propertyCreateDtoToProperty(propertyCreateDto);
        Agent agent = agentRepository.findById(agentId).orElseThrow(() -> new ResourceNotFoundException("Agent", agentId));
        property.setAgent(agent);
        propertyRepository.save(property);
        return propertyMapper.propertyToPropertyGetDto(property);
    }

    @Transactional
    public PropertyGetDto getProperty(Long id) {
        Property property = propertyRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Property", id));
        return propertyMapper.propertyToPropertyGetDto(property);
    }

    public ResponseEntity<Map<String, Object>> getPropertiesByAgent(long id, int page, int size) {
        agentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Agent", id));
        try {
            Pageable paging = PageRequest.of(page, size);
            Page<Property> pageTuts = propertyRepository.findAByAgentOrderById(id, paging);
            List<Property> properties = pageTuts.getContent();

            if (properties.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            Map<String, Object> response = new HashMap<>();
            response.put("properties", properties);
            response.put("currentPage", pageTuts.getNumber());
            response.put("totalItems", pageTuts.getTotalElements());
            response.put("totalPages", pageTuts.getTotalPages());

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
