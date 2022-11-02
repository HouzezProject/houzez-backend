package com.eta.houzezbackend.service;

import com.eta.houzezbackend.dto.PropertyCreateDto;
import com.eta.houzezbackend.dto.PropertyGetDto;
import com.eta.houzezbackend.exception.ResourceNotFoundException;
import com.eta.houzezbackend.mapper.PropertyMapper;
import com.eta.houzezbackend.model.Property;
import com.eta.houzezbackend.repository.PropertyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service
@RequiredArgsConstructor
public class PropertyService {

    private final PropertyRepository propertyRepository;
    private final PropertyMapper propertyMapper;

    public PropertyGetDto createNewProperty(PropertyCreateDto propertyCreateDto) {
        Property property = propertyMapper.propertyCreateDtoToProperty(propertyCreateDto);
        try {
            propertyRepository.save(property);
        } catch (DataIntegrityViolationException e) {
            if (e.getMostSpecificCause().getClass().getName().equals("org.postgresql.util.PSQLException"))
                throw e;

        }
        return propertyMapper.propertyToPropertyGetDto(property);
    }

    @Transactional
    public PropertyGetDto getProperty(Long id) {
        Property property = propertyRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Property", id));
        return propertyMapper.propertyToPropertyGetDto(property);
    }
}
