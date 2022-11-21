package com.eta.houzezbackend.mapper;

import com.eta.houzezbackend.dto.ImageGetDto;
import com.eta.houzezbackend.dto.ImagePostDto;
import com.eta.houzezbackend.model.Image;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ImageMapper {
    Image imagePostDtoToImage(ImagePostDto imagePostDto);

    ImageGetDto imageToImageGetDto(Image image);
}
