package com.eta.houzezbackend.service;

import com.eta.houzezbackend.dto.ImageGetDto;
import com.eta.houzezbackend.dto.PropertyPaginationGetDto;
import com.eta.houzezbackend.dto.PropertyPostDto;
import com.eta.houzezbackend.dto.PropertyGetDto;
import com.eta.houzezbackend.exception.ResourceNotFoundException;
import com.eta.houzezbackend.mapper.ImageMapper;
import com.eta.houzezbackend.mapper.PropertyMapper;
import com.eta.houzezbackend.model.Agent;
import com.eta.houzezbackend.model.Image;
import com.eta.houzezbackend.model.Property;
import com.eta.houzezbackend.repository.ImageRepository;
import com.eta.houzezbackend.repository.PropertyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PropertyService {

    private final PropertyRepository propertyRepository;
    private final PropertyMapper propertyMapper;
    private final AgentService agentService;
    private final ImageMapper imageMapper;

    private final ImageRepository imageRepository;

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
    public PropertyPaginationGetDto getPropertiesByAgent(long id, int page, int size) {
        Pageable paging = PageRequest.of(page, size);
        Page<Property> properties = propertyRepository.findByAgent_Id(id, paging);
        return this.getPagination(properties);
    }

    @Transactional
    public PropertyPaginationGetDto getAllProperty(int page, int size) {
        Pageable paging = PageRequest.of(page, size);
        Page<Property> properties = propertyRepository.findAll(paging);
        return this.getPagination(properties);
    }

    private PropertyPaginationGetDto getPagination(Page<Property> properties){
        List<PropertyGetDto> propertiesGetDto = properties.getContent().stream()
                .map(propertyMapper::propertyToPropertyGetDto)
                .collect(Collectors.toList());
        List<Long> propertiesId = propertiesGetDto.stream().map(PropertyGetDto::getId).toList();


        List<List<Image>> images = propertiesId.stream().map(imageRepository::findByPropertyId).toList();
        List<Image> imageList = images.stream().flatMap(Collection::stream).toList();

        List<ImageGetDto> imageGetDtoList = imageList.stream()
                .map(imageMapper::imageToImageGetDto)
                .toList();

        return PropertyPaginationGetDto.builder().propertyGetDtoList(propertiesGetDto)
                .totalPageNumber(properties.getTotalPages())
                .imageGetDtoList(imageGetDtoList).build();
    }

}
