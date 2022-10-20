package com.eta.houzezbackend.service;

import com.eta.houzezbackend.dto.AgentGetDto;
import com.eta.houzezbackend.dto.PropertyCreateDto;
import com.eta.houzezbackend.dto.PropertyGetDto;
import com.eta.houzezbackend.mapper.PropertyMapper;
import com.eta.houzezbackend.model.Agent;
import com.eta.houzezbackend.model.Property;
import com.eta.houzezbackend.repository.PropertyRepository;
import com.eta.houzezbackend.util.Type;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PropertyServiceTest {
    @Mock
    private PropertyMapper propertyMapper;

    @Mock
    private PropertyRepository propertyRepository;

    @InjectMocks
    private PropertyService propertyService;

    private final PropertyCreateDto MockPropertyCreateDto = PropertyCreateDto.builder().type(Type.House).title("House with sea view").price(800000).living_room(2).bedroom(4).bathroom(3).land_size(200).state("Tas").suburb("Kingston").postcode(7010).agent(Agent.builder().build()).build();
    private final Property mockProperty = Property.builder()
            .type(Type.Apartment)
            .title("Good View")
            .price(300003)
            .property_is_new(true)
            .bedroom(3)
            .land_size(2323)
            .garage(2)
            .indoor("gym")
            .living_room(5)
            .state("TAS")
            .description("Mount house")
            .outdoor("swimming pool")
            .agent(new Agent())
            .postcode(7004)
            .build();


    private final PropertyGetDto mockPropertyGetDto = PropertyGetDto.builder()
            .id(1L)
            .type(Type.Apartment)
            .title("Good View")
            .price(300003)
            .property_is_new(true)
            .bedroom(3)
            .land_size(2323)
            .garage(2)
            .indoor("gym")
            .living_room(5)
            .state("TAS")
            .description("Mount house")
            .outdoor("swimming pool")
            .agent(new AgentGetDto())
            .postcode(7004)
            .createdTime(new Date())
            .updatedTime(new Date())
            .build();

    @Test
    void shouldSaveNewPropertyInPropertyRepoWhenAddNewProperty() {
        when(propertyMapper.propertyCreateDtoToProperty(MockPropertyCreateDto)).thenReturn(mockProperty);
        when(propertyRepository.save(mockProperty)).thenReturn(mockProperty);
        when(propertyMapper.propertyToPropertyGetDto(mockProperty)).thenReturn(mockPropertyGetDto);

        propertyService.createNewProperty(MockPropertyCreateDto);
        assertEquals(propertyService.createNewProperty(MockPropertyCreateDto).getId(), mockPropertyGetDto.getId());
    }

    @Test
    void shouldGetPropertyGetDtoWhenGetProperty() {
        Long mockId = 1L;
        when(propertyRepository.findById(mockId)).thenReturn(Optional.of((mockProperty)));
        when(propertyMapper.propertyToPropertyGetDto(mockProperty)).thenReturn(mockPropertyGetDto);

        propertyService.GetProperty(mockId);
        assertEquals(propertyService.GetProperty(mockId), mockPropertyGetDto);

    }


}
