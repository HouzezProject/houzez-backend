package com.eta.houzezbackend.mapper;

import com.eta.houzezbackend.dto.PropertyCreateDto;
import com.eta.houzezbackend.dto.PropertyGetDto;
import com.eta.houzezbackend.model.Property;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", imports = AgentMapper.class)
public interface PropertyMapper {

    PropertyCreateDto propertyToPropertyCreateDto(Property property);

    Property propertyCreateDtoToProperty(PropertyCreateDto propertyCreateDto);

    PropertyGetDto propertyToPropertyGetDto(Property property);


}
