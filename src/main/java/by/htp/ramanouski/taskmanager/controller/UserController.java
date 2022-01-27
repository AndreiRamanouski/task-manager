package by.htp.ramanouski.taskmanager.controller;


import by.htp.ramanouski.taskmanager.dto.AddressDto;
import by.htp.ramanouski.taskmanager.dto.OrganizationDto;
import by.htp.ramanouski.taskmanager.dto.UserDto;
import by.htp.ramanouski.taskmanager.service.OrganizationService;
import by.htp.ramanouski.taskmanager.service.UserService;
import by.htp.ramanouski.taskmanager.ui.model.request.AddressDetailsRequestModel;
import by.htp.ramanouski.taskmanager.ui.model.request.OrganizationDetailsRequestModel;
import by.htp.ramanouski.taskmanager.ui.model.request.UserDetailsRequestModel;
import by.htp.ramanouski.taskmanager.ui.model.request.UserWithNoOrganizationRequest;
import by.htp.ramanouski.taskmanager.ui.model.response.user.UserRestResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@CrossOrigin
@RequestMapping("/users") // http:///localhost:8080/users
public class UserController {

    private final UserService userService;
    private final OrganizationService organizationService;

    @Autowired
    public UserController(UserService userService,
                          OrganizationService organizationService) {
        this.userService = userService;
        this.organizationService = organizationService;
    }


    @PostMapping("/{userId}/organizations/{organizationId}")
    public UserRestResponse saveUser(@RequestBody UserWithNoOrganizationRequest user,
                                     @PathVariable(name = "organizationId") String organizationId,
                                     @PathVariable(name = "userId") String userId) {
        OrganizationDto organizationDto = organizationService.findByOrganizationId(organizationId);
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(user, userDto);

        userDto.setOrganization(organizationDto);
        userDto.setPassword(organizationDto.getDefaultPassword());
        organizationDto.getUsers().add(userDto);

        UserDto createdUser = userService.createNewUser(userDto);
        ModelMapper mapper = new ModelMapper();
        UserRestResponse returnedValue = mapper.map(createdUser, UserRestResponse.class);
        return returnedValue;
    }


    @GetMapping("/{userId}")
    public UserRestResponse getUser(@PathVariable(name = "userId") String userId) {
        UserDto userDto = userService.getUserByUserId(userId);
        ModelMapper mapper = new ModelMapper();
        UserRestResponse returnedValue = mapper.map(userDto, UserRestResponse.class);

        return returnedValue;
    }
}
