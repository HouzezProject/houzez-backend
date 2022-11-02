package com.eta.houzezbackend.mapper;

import com.eta.houzezbackend.dto.PropertyCreateDto;
import com.eta.houzezbackend.dto.PropertyGetDto;
import com.eta.houzezbackend.model.Property;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", imports = AgentMapper.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PropertyMapper {

    Property propertyCreateDtoToProperty(PropertyCreateDto propertyCreateDto);

    PropertyGetDto propertyToPropertyGetDto(Property property);


}
