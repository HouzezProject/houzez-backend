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

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ImageService {
    private final ImageRepository imageRepository;
    private final PropertyRepository propertyRepository;
    private final AgentRepository agentRepository;
    private final ImageMapper imageMapper;


    public ImageGetDto addImage(ImagePostDto imagePostDto, long agent_id, long property_id) {
        Agent agent = agentRepository.findById(agent_id).orElseThrow(() -> new ResourceNotFoundException("Agent", agent_id));
        Property property = propertyRepository.findById(property_id).orElseThrow(() -> new ResourceNotFoundException("Property", property_id));
        property.setAgent(agent);
        Image image = imageMapper.imagePostDtoToImage(imagePostDto);
        image.setProperty(property);
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
