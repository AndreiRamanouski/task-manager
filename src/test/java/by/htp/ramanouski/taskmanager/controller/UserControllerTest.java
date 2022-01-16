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
import by.htp.ramanouski.taskmanager.ui.model.response.user.UserTaskREST;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserControllerTest {

    @Autowired
    private UserController userController;

    @Autowired
    private UserService userService;

    @Autowired TaskService taskService;

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
        UserDto newUser = userService.createNewUser(createAndInitializeCompleteUserDtoWithOrganizationDto());
        String organizationId = newUser.getOrganization().getOrganizationId();
        UserWithNoOrganizationRequest build = UserWithNoOrganizationRequest.builder()
                .userName("Ivan").lastName("Ivanov").email(newUser.getEmail()).build();
        assertThrows(ServiceException.class, () -> {
            userController.saveUser(build, organizationId, "userId");

        });
    }

    @Test
    void getUser_WithTasksTest() {
        UserDto newUser = userService.createNewUser(createAndInitializeCompleteUserDtoWithOrganizationDto());
        String userId = newUser.getUserId();
        TaskDto taskDto = taskService.saveNewTask(TaskDto.builder()
                .isFinished(false)
                .targetDate(LocalDate.now())
                .title("Title")
                .build(), List.of(userId), userId);

        UserRestResponse userRestResponse = userController.getUser(userId);

        assertThat(userRestResponse.getTasks(), hasSize(1));

        UserTaskREST userTaskREST = userRestResponse.getTasks().get(0);

        assertEquals(taskDto.getTaskId(),userTaskREST.getTaskId());
        assertEquals(taskDto.getTitle(),userTaskREST.getTitle());
        assertEquals(taskDto.getTargetDate(), userTaskREST.getTargetDate());
    }

    @Test
    void getUser() {
        UserDto newUser = userService.createNewUser(createAndInitializeCompleteUserDtoWithOrganizationDto());
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


    private UserDto createAndInitializeCompleteUserDtoWithOrganizationDto(){
        OrganizationDto newOrganization = organizationService.createNewOrganization(organizationDto);
        UserDto userDto = createUserDtoTests();
        userDto.setOrganization(newOrganization);
        return userDto;
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