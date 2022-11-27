package com.eta.houzezbackend.service;

import com.eta.houzezbackend.dto.AgentGetDto;
import com.eta.houzezbackend.dto.PropertyPostDto;
import com.eta.houzezbackend.dto.PropertyGetDto;
import com.eta.houzezbackend.mapper.PropertyMapper;
import com.eta.houzezbackend.model.Agent;
import com.eta.houzezbackend.model.Property;
import com.eta.houzezbackend.repository.PropertyRepository;
import com.eta.houzezbackend.util.PropertyType;
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

    @Mock
    private AgentService agentService;

    @InjectMocks
    private PropertyService propertyService;

    private final PropertyPostDto mockPropertyPostDto = PropertyPostDto.builder()
            .propertyType(PropertyType.HOUSE)
            .title("HOUSE with sea view")
            .price(800000)
            .livingRoom(2)
            .bedroom(4)
            .bathroom(3)
            .landSize(200)
            .state("Tas")
            .suburb("Kingston")
            .postcode(7010)
            .build();

    private final Agent mockAgent = Agent.builder()
            .id(1L)
            .name("name")
            .email("test2@gmail.com")
            .password("123qqqqq.")
            .activated(false)
            .deleted(false)
            .createdTime(new Date())
            .updatedTime(new Date())
            .build();

    private final Property mockProperty = Property.builder()
            .propertyType(PropertyType.APARTMENT)
            .title("Good View")
            .price(300003)
            .preowned(false)
            .bedroom(3)
            .landSize(2323)
            .garage(2)
            .indoor("gym")
            .livingRoom(5)
            .state("TAS")
            .description("Mount house")
            .outdoor("swimming pool")
            .agent(new Agent())
            .postcode(7004)
            .build();


    private final PropertyGetDto mockPropertyGetDto = PropertyGetDto.builder()
            .id(1L)
            .propertyType(PropertyType.APARTMENT)
            .title("Good View")
            .price(300003)
            .preowned(true)
            .bedroom(3)
            .landSize(2323)
            .garage(2)
            .indoor("gym")
            .livingRoom(5)
            .state("TAS")
            .description("Mount house")
            .outdoor("swimming pool")
            .agent(new AgentGetDto())
            .postcode(7004)
            .createdTime(new Date())
            .updatedTime(new Date())
            .build();
    private final long mockAgentId = mockAgent.getId();

    @Test
    void shouldSaveNewPropertyInPropertyRepoWhenAddNewProperty() {
        when(propertyMapper.propertyCreateDtoToProperty(mockPropertyPostDto)).thenReturn(mockProperty);
        when(agentService.find(mockAgentId)).thenReturn(mockAgent);
        when(propertyRepository.save(mockProperty)).thenReturn(mockProperty);
        when(propertyMapper.propertyToPropertyGetDto(mockProperty)).thenReturn(mockPropertyGetDto);

        propertyService.createNewProperty(mockPropertyPostDto, mockAgentId);
        assertEquals(propertyService.createNewProperty(mockPropertyPostDto, mockAgentId).getId(), mockPropertyGetDto.getId());
    }

    @Test
    void shouldReturnSuccessWhenGetProperty() {
        Long mockId = 1L;
        when(propertyRepository.findById(mockId)).thenReturn(Optional.of((mockProperty)));
        when(propertyMapper.propertyToPropertyGetDto(mockProperty)).thenReturn(mockPropertyGetDto);

        propertyService.getProperty(mockId);
        assertEquals(propertyService.getProperty(mockId), mockPropertyGetDto);
    }

}
