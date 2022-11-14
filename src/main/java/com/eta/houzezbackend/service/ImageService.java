package com.eta.houzezbackend.service;

import com.eta.houzezbackend.dto.ImageGetDto;
import com.eta.houzezbackend.dto.ImagePostDto;
import com.eta.houzezbackend.exception.ResourceNotFoundException;
import com.eta.houzezbackend.mapper.ImageMapper;
import com.eta.houzezbackend.model.Agent;
import com.eta.houzezbackend.model.Image;
import com.eta.houzezbackend.model.Property;
import com.eta.houzezbackend.repository.AgentRepository;
import com.eta.houzezbackend.repository.ImageRepository;
import com.eta.houzezbackend.repository.PropertyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;
    private final PropertyRepository propertyRepository;
    private final AgentRepository agentRepository;
    private final ImageMapper imageMapper;
    private final AmazonClientService amazonClientService;


    public ImageGetDto addImage(MultipartFile multipartFile, long propertyId) {
        ImagePostDto imagePostDto = ImagePostDto.builder().url("url").tag("tag").build();

        Property property = propertyRepository.findById(propertyId).orElseThrow(() -> new ResourceNotFoundException("Property", propertyId));
        Image image = imageMapper.imagePostDtoToImage(imagePostDto);
        image.setProperty(property);
        imageRepository.save(image);
        image.setUrl(amazonClientService.uploadFile(multipartFile, image.getId()));
        imageRepository.save(image);
        return imageMapper.imageToImageGetDto(image);

    }

    @Transactional(readOnly = true)
    public List<ImageGetDto> getImage(long id, int page, int size) {
        Pageable paging = PageRequest.of(page, size);
        Page<Image> properties = imageRepository.findByPropertyId(id, paging);
        return properties.getContent().stream()
                .map(imageMapper::imageToImageGetDto)
                .collect(Collectors.toList());

    }
}
