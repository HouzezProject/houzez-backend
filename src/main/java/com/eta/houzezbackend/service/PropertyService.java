package com.eta.houzezbackend.service;

import com.eta.houzezbackend.dto.PropertyGetDto;
import com.eta.houzezbackend.dto.PropertyPaginationGetDto;
import com.eta.houzezbackend.dto.PropertyPostDto;
import com.eta.houzezbackend.exception.PropertyNotFoundException;
import com.eta.houzezbackend.exception.ResourceNotFoundException;
import com.eta.houzezbackend.mapper.PropertyMapper;
import com.eta.houzezbackend.model.Agent;
import com.eta.houzezbackend.model.Property;
import com.eta.houzezbackend.repository.PropertyRepository;
import com.eta.houzezbackend.util.PropertyType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
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

    @Transactional
    public PropertyPaginationGetDto getPropertyByCriteria(int page, int size, int livingRoom, int bedroom,
                                                          int bathroom, int garage, double maxLatitude, double maxLongitude,
                                                          double minLatitude, double minLongitude, int postcode, int minPrice,
                                                          int maxPrice, String propertyType) {

        Pageable paging = PageRequest.of(page, size);
        Page<Property> result;
        Specification<Property> queryCondition = (root, query, criteriaBuilder) -> {
            List<Predicate> predicateList = new ArrayList<>();
            if (livingRoom != 0) {
                predicateList.add(criteriaBuilder.greaterThan(root.get("livingRoom"), livingRoom - 1));
            }
            if (bedroom != 0) {
                predicateList.add(criteriaBuilder.greaterThan(root.get("bedroom"), bedroom - 1));
            }
            if (bathroom != 0) {
                predicateList.add(criteriaBuilder.greaterThan(root.get("bathroom"), bathroom - 1));
            }
            if (garage != 0) {
                predicateList.add(criteriaBuilder.greaterThan(root.get("garage"), garage - 1));
            }
            if (maxLatitude != -10 && minLatitude != -43) {
                predicateList.add(criteriaBuilder.between(root.get("latitude"), minLatitude, maxLatitude));
            }
            if (maxLongitude != 154 && minLongitude != 112) {
                predicateList.add(criteriaBuilder.between(root.get("longitude"), minLongitude, maxLongitude));
            }
            if (postcode != 0) {
                predicateList.add(criteriaBuilder.equal(root.get("postcode"), postcode));
            }
            if (maxPrice != 100000000 || minPrice != 0) {
                predicateList.add(criteriaBuilder.between(root.get("price"), minPrice, maxPrice));
            }
            if (!propertyType.isEmpty()) {
                PropertyType newPropertyType = PropertyType.valueOf(propertyType.toUpperCase());
                predicateList.add(criteriaBuilder.equal(root.get("propertyType"), newPropertyType));
            }
            return criteriaBuilder.and(predicateList.toArray(new Predicate[0]));
        };
        try {
            result = propertyRepository.findAll(queryCondition, paging);
        } catch (Exception e) {
            throw new PropertyNotFoundException();
        }
        return this.getPagination(result);
    }

    private PropertyPaginationGetDto getPagination(Page<Property> properties) {

        List<PropertyGetDto> propertiesGetDto = properties.getContent().stream()
                .map(propertyMapper::propertyToPropertyGetDto)
                .collect(Collectors.toList());

        return PropertyPaginationGetDto.builder().propertyGetDtoList(propertiesGetDto)
                .totalPropertyNumber(properties.getTotalElements()).build();

    }

}
