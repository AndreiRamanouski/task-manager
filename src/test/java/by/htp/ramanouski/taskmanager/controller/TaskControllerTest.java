package by.htp.ramanouski.taskmanager.controller;

import by.htp.ramanouski.taskmanager.dto.AddressDto;
import by.htp.ramanouski.taskmanager.dto.OrganizationDto;
import by.htp.ramanouski.taskmanager.dto.UserDto;
import by.htp.ramanouski.taskmanager.service.OrganizationService;
import by.htp.ramanouski.taskmanager.service.UserService;
import by.htp.ramanouski.taskmanager.service.exception.ServiceException;
import by.htp.ramanouski.taskmanager.ui.model.request.TaskDetailsRequestModel;
import by.htp.ramanouski.taskmanager.ui.model.response.task.TaskRestResponse;
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
class TaskControllerTest {

    @Autowired
    private TaskController taskController;

    @Autowired
    private OrganizationService organizationService;

    @Autowired
    private UserService userService;


    private OrganizationDto organizationDto;
    private UserDto userDto;

    @BeforeEach
    void setUp() {
        organizationDto = createOrganizationDtoTests();
        userDto = createUserDtoTests();
    }

    @AfterEach
    void tearDown() {
    }


    @Test
    void getTask() {
        UserDto userDto = saveUserAndOrganizationInDatabase();
        TaskRestResponse restResponse = createAndSaveTaskToDatabase(userDto.getUserId());

        TaskRestResponse task = taskController.getTask(userDto.getOrganization().getOrganizationId(), restResponse.getTaskId());
        assertNotNull(task);
        assertThat(task.getUsers(), hasSize(1));
    }

    @Test
    void createTask_GET() {
        UserDto userDto = saveUserAndOrganizationInDatabase();
        TaskRestResponse task = taskController.createTask(userDto.getOrganization().getOrganizationId());
        assertNotNull(task);
    }

    @Test
    void createTask_GET_ThrowsServiceException() {
        assertThrows(ServiceException.class, () -> {
            taskController.createTask("randomId");
        });
    }


    @Test
    void createTask_POST() {
        UserDto userDto = saveUserAndOrganizationInDatabase();

        TaskRestResponse restResponse = createAndSaveTaskToDatabase(userDto.getUserId());

        assertNotNull(restResponse);
        assertThat(restResponse.getUsers(), hasSize(1));

    }

    private TaskRestResponse createAndSaveTaskToDatabase(String userId) {
        TaskDetailsRequestModel taskDetailsRequestModel = TaskDetailsRequestModel.builder()
                .targetDate(LocalDate.now())
                .usersPublicIdToAssign(List.of(userId))
                .title("Task Test")
                .build();

        TaskRestResponse returnedValue = taskController.createTask(taskDetailsRequestModel,
                userDto.getOrganization().getOrganizationId()
                , userDto.getUserId());
        return returnedValue;
    }

    private UserDto saveUserAndOrganizationInDatabase() {
        OrganizationDto newOrganization = organizationService.createNewOrganization(organizationDto);
        userDto.setOrganization(newOrganization);
        UserDto returnedValue = userService.createNewAdminUser(userDto);
        return returnedValue;
    }

    private OrganizationDto createOrganizationDtoTests() {
        return OrganizationDto.builder()
                .organizationName("Organization")
                .defaultPassword("DefaultPassword")
                .phoneNumber("123-34-45")
                .organizationId("testId")
                .address(AddressDto.builder()
                        .countryName("CountryName")
                        .streetName("StreetName")
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