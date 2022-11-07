package com.eta.houzezbackend.mapper;

import com.eta.houzezbackend.dto.ImageGetDto;
import com.eta.houzezbackend.dto.ImagePostDto;
import com.eta.houzezbackend.dto.PropertyPostDto;
import com.eta.houzezbackend.model.Image;
import com.eta.houzezbackend.model.Property;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", imports = PropertyMapper.class, unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ImageMapper {
    Image imagePostDtoToImage(ImagePostDto imagePostDto);

    ImageGetDto imageToImageGetDto(Image image);
}
