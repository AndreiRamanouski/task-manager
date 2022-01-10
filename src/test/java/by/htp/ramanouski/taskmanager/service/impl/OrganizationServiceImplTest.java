package by.htp.ramanouski.taskmanager.service.impl;

import by.htp.ramanouski.taskmanager.dto.AddressDto;
import by.htp.ramanouski.taskmanager.dto.OrganizationDto;
import by.htp.ramanouski.taskmanager.entity.AddressEntity;
import by.htp.ramanouski.taskmanager.entity.OrganizationEntity;
import by.htp.ramanouski.taskmanager.entity.TaskEntity;
import by.htp.ramanouski.taskmanager.entity.UserEntity;
import by.htp.ramanouski.taskmanager.repository.OrganizationRepository;
import by.htp.ramanouski.taskmanager.service.ServiceUtils;
import by.htp.ramanouski.taskmanager.service.exception.ServiceException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

class OrganizationServiceImplTest {

    @InjectMocks
    private OrganizationServiceImpl organizationServiceImpl;

    @Mock
    private ServiceUtils serviceUtils;

    @Mock
    private OrganizationRepository organizationRepository;

    private OrganizationEntity organizationEntity;
    private ModelMapper mapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        mapper = new ModelMapper();
        organizationEntity = new OrganizationEntity();
        organizationEntity.setOrganizationName("Organization1");
        organizationEntity.setDefaultPassword("defaultPassword");
        organizationEntity.setPhoneNumber("222-33-44");
        organizationEntity.setAddress(createAddressEntity());
        organizationEntity.setUsers(createMockUsersEntities());
    }

    @Test
    void findByOrganizationName() {
        when(organizationRepository.findByOrganizationName(anyString())).thenReturn(organizationEntity);
        OrganizationEntity organization = organizationServiceImpl.findByOrganizationName("Mock");

        assertNotNull(organization);
        assertNotNull(organization.getAddress());
        assertThat(organization.getUsers(), hasSize(2));
    }

    @Test
    void findByOrganizationName_ReturnsNull() {
        when(organizationRepository.findByOrganizationName(anyString())).thenReturn(null);
        OrganizationEntity organization = organizationServiceImpl.findByOrganizationName("Mock");
        assertNull(organization);
    }

    @Test
    void findByOrganizationId() {
        when(organizationRepository.findByOrganizationId(anyString())).thenReturn(organizationEntity);
        OrganizationDto organizationDto = organizationServiceImpl.findByOrganizationId("o");

        assertNotNull(organizationDto);
        assertEquals("Organization1", organizationDto.getOrganizationName());
        assertThat(organizationDto.getUsers(), hasSize(2));
    }


    @Test
    void findByOrganizationId_ThrowsException() {
        when(organizationRepository.findByOrganizationId(anyString())).thenReturn(null);
        assertThrows(ServiceException.class, () -> organizationServiceImpl.findByOrganizationId("sss"));
    }

    @Test
    void createNewOrganization() {
        when(organizationRepository.findByOrganizationName(anyString())).thenReturn(null);
        when(organizationRepository.save(any(OrganizationEntity.class))).thenReturn(organizationEntity);
        OrganizationDto organizationDto = createOrganizationDto();
        OrganizationDto savedOrganization = organizationServiceImpl.createNewOrganization(organizationDto);

        assertNotNull(savedOrganization);
        assertEquals(organizationEntity.getOrganizationId(), savedOrganization.getOrganizationId());
        assertEquals(organizationEntity.getOrganizationName(), savedOrganization.getOrganizationName());
        assertThat(savedOrganization.getUsers(),hasSize(2));
    }

    @Test
    void createNewOrganization_ThrowsException() {
        when(organizationRepository.findByOrganizationName(anyString())).thenReturn(organizationEntity);
        OrganizationDto organizationDto = createOrganizationDto();
        assertThrows(ServiceException.class, () -> organizationServiceImpl.createNewOrganization(organizationDto));
    }

    private AddressDto createAddressDto(){
        return mapper.map(createAddressEntity(),AddressDto.class);
    }


    private OrganizationDto createOrganizationDto(){
        AddressDto addressDto = createAddressDto();
        OrganizationDto organizationDto = new OrganizationDto();
        organizationDto.setDefaultPassword("default");
        organizationDto.setOrganizationName("Organization1");
        organizationDto.setAddress(addressDto);
        organizationDto.setPhoneNumber("111-22-33");
        return organizationDto;
    }

    private AddressEntity createAddressEntity() {
        return AddressEntity.builder().addressId(serviceUtils.generatePublicAddressId())
                .countryName("MockCountry").streetName("MockStreet")
                .build();
    }


    private List<UserEntity> createMockUsersEntities() {
        List<UserEntity> userEntities = new ArrayList<>();
        UserEntity userEntity1 = UserEntity.builder().userName("Mock1").lastName("Mock1")
                .email("mockemail1@mail.ru").encryptedPassword("mockPassword1")
                .userId(serviceUtils.generatePublicUserId())
                .build();
        UserEntity userEntity2 = UserEntity.builder().userName("Mock2").lastName("Mock2")
                .email("mockemail2@mail.ru").encryptedPassword("mockPassword2")
                .userId(serviceUtils.generatePublicUserId())
                .build();
        userEntities.add(userEntity1);
        userEntities.add(userEntity2);
        return userEntities;
    }

}