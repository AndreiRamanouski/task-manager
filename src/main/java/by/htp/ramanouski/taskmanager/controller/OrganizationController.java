package by.htp.ramanouski.taskmanager.controller;

import by.htp.ramanouski.taskmanager.dto.OrganizationDto;
import by.htp.ramanouski.taskmanager.dto.UserDto;
import by.htp.ramanouski.taskmanager.service.OrganizationService;
import by.htp.ramanouski.taskmanager.service.UserService;
import by.htp.ramanouski.taskmanager.ui.model.request.OrganizationDetailsRequestModel;
import by.htp.ramanouski.taskmanager.ui.model.request.UserDetailsRequestModel;
import by.htp.ramanouski.taskmanager.ui.model.response.organization.OrganizationRestResponse;
import by.htp.ramanouski.taskmanager.ui.model.response.user.UserRestResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@CrossOrigin
@RequestMapping("/organizations")  // http:///localhost:8080/users
public class OrganizationController {

    private final OrganizationService organizationService;
    private final UserService userService;

    @Autowired
    public OrganizationController(OrganizationService organizationService,
                                  UserService userService) {
        this.organizationService = organizationService;
        this.userService = userService;
    }



    @PostMapping
    public UserRestResponse createUserAndOrganization(@RequestBody UserDetailsRequestModel userDetails) {

        ModelMapper mapper = new ModelMapper();
        OrganizationDetailsRequestModel organization = userDetails.getOrganization();
        OrganizationDto organizationDto = mapper.map(organization, OrganizationDto.class);
        OrganizationDto savedOrganization = organizationService.createNewOrganization(organizationDto);

        UserDto userDto = mapper.map(userDetails, UserDto.class);
        userDto.setOrganization(savedOrganization);
        UserDto createdUser = userService.createNewAdminUser(userDto);

        UserRestResponse returnedValue = mapper.map(createdUser, UserRestResponse.class);

        return returnedValue;
    }

    @GetMapping("/{organizationId}")
    public OrganizationRestResponse getOrganization(@PathVariable String organizationId){
        OrganizationDto organizationDto = organizationService.findByOrganizationId(organizationId);

        ModelMapper mapper = new ModelMapper();
        OrganizationRestResponse returnedValue = mapper.map(organizationDto, OrganizationRestResponse.class);

        return returnedValue;
    }


}
