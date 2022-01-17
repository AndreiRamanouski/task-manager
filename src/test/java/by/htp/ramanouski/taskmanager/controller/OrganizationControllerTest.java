package by.htp.ramanouski.taskmanager.controller;

import by.htp.ramanouski.taskmanager.service.OrganizationService;
import by.htp.ramanouski.taskmanager.service.UserService;
import by.htp.ramanouski.taskmanager.service.exception.ServiceException;
import by.htp.ramanouski.taskmanager.ui.model.request.AddressDetailsRequestModel;
import by.htp.ramanouski.taskmanager.ui.model.request.OrganizationDetailsRequestModel;
import by.htp.ramanouski.taskmanager.ui.model.request.UserDetailsRequestModel;
import by.htp.ramanouski.taskmanager.ui.model.request.UserWithNoOrganizationRequest;
import by.htp.ramanouski.taskmanager.ui.model.response.organization.OrganizationRestResponse;
import by.htp.ramanouski.taskmanager.ui.model.response.organization.OrganizationUserREST;
import by.htp.ramanouski.taskmanager.ui.model.response.user.UserRestResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class OrganizationControllerTest {

    @Autowired
    private OrganizationController organizationController;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void createUserAndOrganization() {
        UserDetailsRequestModel userAndOrganizationToSave = createUserDetailsRequestModel();
        UserRestResponse userAndOrganization = organizationController.createUserAndOrganization(userAndOrganizationToSave);
        assertNotNull(userAndOrganization);
        assertEquals(userAndOrganizationToSave.getEmail(), userAndOrganization.getEmail());

    }

    @Test
    void getOrganization() {
        UserRestResponse userAndOrganization = organizationController.createUserAndOrganization(createUserDetailsRequestModel());
        String organizationId = userAndOrganization.getOrganization().getOrganizationId();

        OrganizationRestResponse organization = organizationController.getOrganization(organizationId);
        assertNotNull(organization);
        assertEquals(organizationId, organization.getOrganizationId());
        assertEquals(userAndOrganization.getOrganization().getOrganizationName(), organization.getOrganizationName());

    }

    @Test
    void getOrganization_ThrowsServiceException() {
        assertThrows(ServiceException.class, () -> {
            organizationController.getOrganization("123");
        });
    }


    private UserDetailsRequestModel createUserDetailsRequestModel() {
        return UserDetailsRequestModel.builder()
                .userName("Ivan")
                .lastName("Ivanov")
                .email("ivanov@gmail.com")
                .password("test123")
                .organization(OrganizationDetailsRequestModel.builder()
                        .organizationName("Orange")
                        .phoneNumber("7788")
                        .defaultPassword("vasya228")
                        .address(AddressDetailsRequestModel.builder()
                                .streetName("Red Square")
                                .countryName("Moskow")
                                .build()
                        ).build()
                ).build();
    }


}