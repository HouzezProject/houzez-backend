package com.eta.houzezbackend.mapper;

import com.eta.houzezbackend.dto.PropertyPostDto;
import com.eta.houzezbackend.dto.PropertyGetDto;
import com.eta.houzezbackend.model.Property;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", imports = AgentMapper.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PropertyMapper {

    Property propertyCreateDtoToProperty(PropertyPostDto propertyCreateDto);

    PropertyGetDto propertyToPropertyGetDto(Property property);

    Property propertyGetDtoToProperty(PropertyGetDto propertyGetDto);
}
