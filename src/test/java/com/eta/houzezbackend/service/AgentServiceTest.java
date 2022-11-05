package com.eta.houzezbackend.service;

import com.eta.houzezbackend.dto.AgentGetDto;
import com.eta.houzezbackend.dto.AgentSignUpDto;
import com.eta.houzezbackend.dto.PropertyCreateDto;
import com.eta.houzezbackend.dto.PropertyGetDto;
import com.eta.houzezbackend.mapper.AgentMapper;
import com.eta.houzezbackend.model.Agent;
import com.eta.houzezbackend.model.Property;
import com.eta.houzezbackend.repository.AgentRepository;

import com.eta.houzezbackend.service.email.EmailService;
import com.eta.houzezbackend.util.PropertyType;
import com.eta.houzezbackend.util.SystemParam;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AgentServiceTest {
    @Mock
    private AgentRepository agentRepository;
    @Mock
    private AgentMapper agentMapper;
    @Mock
    private JwtService jwtService;
    @Mock
    private SystemParam systemParam;
    @Mock
    private EmailService emailService;
    @InjectMocks
    private AgentService agentService;

    private final AgentSignUpDto mockAgentSignUpDto = AgentSignUpDto
            .builder()
            .email("test2@gmail.com")
            .password("123qqqqq.")
            .build();

    private final AgentGetDto mockAgentGetDto = AgentGetDto
            .builder().id(1L)
            .name("name")
            .email("test2@gmail.com")
            .activated(false)
            .deleted(false)
            .createdTime(new Date())
            .updatedTime(new Date())
            .build();

    private final Claims claims = Jwts.claims().setId("123").setSubject("name").setExpiration(new Date(System.currentTimeMillis() + (5 * 60 * 1000)));

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
    private final PropertyCreateDto MockPropertyCreateDto = PropertyCreateDto.builder().propertyType(PropertyType.HOUSE).title("HOUSE with sea view").price(800000).livingRoom(2).bedroom(4).bathroom(3).landSize(200).state("Tas").suburb("Kingston").postcode(7010).build();
    private final Property mockProperty = Property.builder()
            .propertyType(PropertyType.APARTMENT)
            .title("Good View")
            .price(300003)
            .propertyIsNew(true)
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
            .propertyIsNew(true)
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

    @Test
    void shouldSaveNewAgentInAgentRepoWhenSignUpNewAgent() {


        when(agentMapper.agentSignUpDtoToAgent(mockAgentSignUpDto)).thenReturn(mockAgent);
        when(agentRepository.save(mockAgent)).thenReturn(mockAgent);
        when(agentMapper.agentToAgentGetDto(mockAgent)).thenReturn(mockAgentGetDto);
        when(systemParam.getBaseUrl()).thenReturn("http://localhost:8080/api/v1");
        doNothing().when(emailService).sendEmail(eq(mockAgent.getEmail()), any(), any());
        agentService.signUpNewAgent(mockAgentSignUpDto);
        assertEquals(agentService.signUpNewAgent(mockAgentSignUpDto).getId(), mockAgentGetDto.getId());
    }

    @Test
    void shouldGetAgentGetDtoWhenGetAgent() {
        Long mockId = 1L;
        when(agentMapper.agentToAgentGetDto(mockAgent)).thenReturn(mockAgentGetDto);
        when(agentRepository.findById(mockId)).thenReturn(Optional.of(mockAgent));

        assertEquals(mockAgentGetDto, agentService.getAgent(mockId));
    }

    @Test
    void shouldGetActiveLinkWhenCreateSignUpLink() {
        String baseUrl = "base";
        String id = "id";
        String name = "name";
        int minute = 10;
        String jwt = "jwt";
        when(jwtService.createJWT(id, name, minute)).thenReturn(jwt);
        assertEquals(agentService.createSignUpLink(baseUrl, id, name, minute), baseUrl + "/verification?code=" + jwt);
    }

    @Test
    void shouldActiveAgentWhenSetAgentToActive() {
        String jwt = "jwt";
        when(jwtService.getJwtBody(jwt)).thenReturn(claims);
        when(agentRepository.findById(Long.parseLong(claims.getId()))).thenReturn(Optional.of(mockAgent));
        when(agentRepository.save(mockAgent)).thenReturn(mockAgent);
        assertEquals(agentService.setAgentToActive(jwt), mockAgent);

    }

    @Test
    void shouldGetAgentWhenFindByEmail() {
        shouldSaveNewAgentInAgentRepoWhenSignUpNewAgent();
        String mockEmail = "test2@gmail.com";
        when(agentRepository.findByEmail(mockEmail)).thenReturn(Optional.of(mockAgent));
        assertEquals(agentService.findByEmail(mockEmail), mockAgent);
    }

    @Test
    void shouldGetAgentGetDtoWhenForgetEmailSent() {
        String mockEmail = "test2@gmail.com";
        when(agentRepository.findByEmail(mockEmail)).thenReturn(Optional.of(mockAgent));
        assertEquals(agentService.findByEmail(mockEmail), mockAgent);
        lenient().when(jwtService.createResetPasswordJWT(10, mockAgent.getEmail())).thenReturn("jwt");
        lenient().when(systemParam.getBaseUrl()).thenReturn("http://localhost:8080/api/v1");
        String resetLink = "http://localhost:8080/api/v1" + "/reset-password?code=" + "jwt";
        lenient().doNothing().when(emailService).sendEmail(mockAgent.getEmail(), resetLink, "forgetPassword");
    }
}