package com.eta.houzezbackend.mapper;

import com.eta.houzezbackend.dto.PropertyPostDto;
import com.eta.houzezbackend.dto.PropertyGetDto;
import com.eta.houzezbackend.model.Property;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", imports = AgentMapper.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PropertyMapper {

    @Mapping(target = "state", constant = "true")
    Property propertyCreateDtoToProperty(PropertyPostDto propertyCreateDto);

    PropertyGetDto propertyToPropertyGetDto(Property property);

    Property propertyGetDtoToProperty(PropertyGetDto propertyGetDto);
}
