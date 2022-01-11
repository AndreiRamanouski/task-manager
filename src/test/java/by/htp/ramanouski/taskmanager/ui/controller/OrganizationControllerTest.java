package by.htp.ramanouski.taskmanager.ui.controller;

import by.htp.ramanouski.taskmanager.dto.AddressDto;
import by.htp.ramanouski.taskmanager.dto.OrganizationDto;
import by.htp.ramanouski.taskmanager.dto.UserDto;
import by.htp.ramanouski.taskmanager.service.OrganizationService;
import by.htp.ramanouski.taskmanager.service.utils.ServiceUtils;
import by.htp.ramanouski.taskmanager.service.UserService;
import by.htp.ramanouski.taskmanager.ui.controller.exception.ControllerException;
import by.htp.ramanouski.taskmanager.ui.model.request.AddressDetailsRequestModel;
import by.htp.ramanouski.taskmanager.ui.model.request.OrganizationDetailsRequestModel;
import by.htp.ramanouski.taskmanager.ui.model.request.UserDetailsRequestModel;
import by.htp.ramanouski.taskmanager.ui.model.response.organization.OrganizationRestResponse;
import by.htp.ramanouski.taskmanager.ui.model.response.user.UserOrganizationREST;
import by.htp.ramanouski.taskmanager.ui.model.response.user.UserRestResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class OrganizationControllerTest {

    @InjectMocks
    private OrganizationController organizationController;

    @Mock
    private OrganizationService organizationService;

    @Mock
    private UserService userService;

    @Mock
    private ServiceUtils serviceUtils;

    OrganizationDto organizationDto;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        AddressDto addressDto = createMockAddressDto();
        organizationDto = new OrganizationDto();
        OrganizationDto organizationDto = new OrganizationDto();
        organizationDto.setDefaultPassword("defaultPassword");
        organizationDto.setOrganizationName("Organization");
        organizationDto.setAddress(addressDto);
        organizationDto.setPhoneNumber("222-33-44");
        organizationDto.setUsers(new ArrayList<>());
    }

    @Test
    void createUserAndOrganization() {
        when(organizationService.createNewOrganization(any(OrganizationDto.class))).thenReturn(organizationDto);
        UserDto userDto = createMockUserDto();
        userDto.setOrganization(organizationDto);
        when(userService.createUser(any(UserDto.class))).thenReturn(userDto);
        UserDetailsRequestModel userDetailsRequestModel = UserDetailsRequestModel.builder()
                .userName("MockName").lastName("MockLastName")
                .email("mock@mail.ru").password("123")
                .organization(OrganizationDetailsRequestModel.builder()
                        .organizationName("OrganizationMock")
                        .defaultPassword("DefaultPassword")
                        .phoneNumber("123-34-45")
                        .address(AddressDetailsRequestModel.builder()
                                .countryName("MockCountryName")
                                .streetName("MockStreetName")
                                .build())
                        .build())
                .build();

        UserRestResponse userRestResponse = organizationController.createUserAndOrganization(userDetailsRequestModel);
        UserOrganizationREST organizationREST = userRestResponse.getOrganization();
        OrganizationDto organizationDto = userDto.getOrganization();
        assertNotNull(userRestResponse);
        assertEquals(organizationREST.getOrganizationName(), organizationDto.getOrganizationName());
        assertEquals(organizationREST.getOrganizationId(),organizationDto.getOrganizationId());
        assertEquals(organizationREST.getAddress(), organizationDto.getAddress());
        assertEquals(userRestResponse.getUserName(),userDto.getUserName());
        assertEquals(userRestResponse.getUserId(),userDto.getUserId());
    }

    @Test
    void getOrganization() {
        when(organizationService.findByOrganizationId(anyString())).thenReturn(organizationDto);
        OrganizationRestResponse organizationRestResponse = organizationController.getOrganization("mock");
        assertNotNull(organizationRestResponse);
        assertEquals(organizationRestResponse.getOrganizationId(),organizationDto.getOrganizationId());
        assertEquals(organizationRestResponse.getOrganizationName(), organizationDto.getOrganizationName());

        assertEquals(organizationRestResponse.getAddress(),organizationDto.getAddress());
    }

    @Test
    void getOrganization_ThrowsException() {
        when(organizationService.findByOrganizationId(anyString())).thenReturn(null);
        assertThrows(ControllerException.class, () -> organizationController.getOrganization("mock"));
    }

    private AddressDto createMockAddressDto() {
        AddressDto addressDto = new AddressDto();
        addressDto.setAddressId(serviceUtils.generatePublicAddressId());
        addressDto.setCountryName("MockCountry");
        addressDto.setStreetName("MockStreet");
        return addressDto;
    }

    private UserDto createMockUserDto() {
        UserDto userDto = UserDto.builder().userName("Mock1").lastName("Mock1")
                .email("mockemail1@mail.ru").encryptedPassword("mockPassword1")
                .userId(serviceUtils.generatePublicUserId())
                .build();

        return userDto;
    }
}