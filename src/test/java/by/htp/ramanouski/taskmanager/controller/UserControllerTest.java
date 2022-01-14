package by.htp.ramanouski.taskmanager.controller;

import by.htp.ramanouski.taskmanager.dto.AddressDto;
import by.htp.ramanouski.taskmanager.dto.OrganizationDto;
import by.htp.ramanouski.taskmanager.dto.TaskDto;
import by.htp.ramanouski.taskmanager.dto.UserDto;
import by.htp.ramanouski.taskmanager.entity.AddressEntity;
import by.htp.ramanouski.taskmanager.entity.OrganizationEntity;
import by.htp.ramanouski.taskmanager.entity.UserEntity;
import by.htp.ramanouski.taskmanager.repository.UserRepository;
import by.htp.ramanouski.taskmanager.service.OrganizationService;
import by.htp.ramanouski.taskmanager.service.TaskService;
import by.htp.ramanouski.taskmanager.service.UserService;
import by.htp.ramanouski.taskmanager.service.exception.ServiceException;
import by.htp.ramanouski.taskmanager.service.utils.ServiceUtils;
import by.htp.ramanouski.taskmanager.ui.model.request.UserWithNoOrganizationRequest;
import by.htp.ramanouski.taskmanager.ui.model.response.user.UserRestResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserControllerTest {

    @Autowired
    private UserController userController;

    @Autowired
    private UserService userService;

    @Autowired
    private OrganizationService organizationService;

    private UserWithNoOrganizationRequest userWithNoOrganizationRequest;
    private OrganizationDto organizationDto;


    @BeforeEach
    void setUp() {
        organizationDto = createOrganizationDtoTests();
        userWithNoOrganizationRequest = UserWithNoOrganizationRequest.builder()
                .userName("Ivan").lastName("Ivanov").email("ivanov@ya.ru").build();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void saveUser() {
        OrganizationDto newOrganization = organizationService.createNewOrganization(organizationDto);

        String organizationId = newOrganization.getOrganizationId();
        UserRestResponse userRestResponse =
                userController.saveUser(userWithNoOrganizationRequest, organizationId, "userId");
        assertNotNull(userRestResponse);
        assertEquals(userWithNoOrganizationRequest.getEmail(), userRestResponse.getEmail());
    }

    @Test
    void saveUser_ThrowsServiceException() {
        OrganizationDto newOrganization = organizationService.createNewOrganization(organizationDto);
        UserDto userDto = createUserDtoTests();
        String organizationId = newOrganization.getOrganizationId();
        userDto.setOrganization(newOrganization);
        userService.createNewUser(userDto);
        UserWithNoOrganizationRequest build = UserWithNoOrganizationRequest.builder()
                .userName("Ivan").lastName("Ivanov").email(userDto.getEmail()).build();
        assertThrows(ServiceException.class, () -> {
            userController.saveUser(build, organizationId, "userId");

        });
    }


    @Test
    void getUser() {

        OrganizationDto newOrganization = organizationService.createNewOrganization(organizationDto);
        UserDto userDto = createUserDtoTests();
        userDto.setOrganization(newOrganization);
        UserDto newUser = userService.createNewUser(userDto);
        String userId = newUser.getUserId();

        UserRestResponse userRestResponse = userController.getUser(userId);

        assertNotNull(userRestResponse);
        assertEquals(newUser.getUserId(), userRestResponse.getUserId());
        assertEquals(newUser.getOrganization().getOrganizationId(),userRestResponse.getOrganization().getOrganizationId());

    }

    @Test
    void getUser_ThrowsServiceException() {
        assertThrows(ServiceException.class, ()->{
            userController.getUser("1234");
        });

    }

    private OrganizationDto createOrganizationDtoTests() {
        return OrganizationDto.builder()
                .organizationName("OrganizationMock")
                .defaultPassword("DefaultPassword")
                .phoneNumber("123-34-45")
                .organizationId("testId")
                .address(AddressDto.builder()
                        .countryName("MockCountryName")
                        .streetName("MockStreetName")
                        .build())
                .build();
    }

    private UserDto createUserDtoTests() {
        return UserDto.builder()
                .userName("Petr").lastName("Petrov")
                .email("petrov@mail.ru")
                .password("123")
                .build();
    }
}