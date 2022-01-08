package by.htp.ramanouski.taskmanager.ui.controller;


import by.htp.ramanouski.taskmanager.dto.OrganizationDto;
import by.htp.ramanouski.taskmanager.dto.UserDto;
import by.htp.ramanouski.taskmanager.service.OrganizationService;
import by.htp.ramanouski.taskmanager.service.UserService;
import by.htp.ramanouski.taskmanager.ui.model.request.OrganizationDetailsRequestModel;
import by.htp.ramanouski.taskmanager.ui.model.request.UserDetailsRequestModel;
import by.htp.ramanouski.taskmanager.ui.model.request.UserWithNoOrganizationRequest;
import by.htp.ramanouski.taskmanager.ui.model.response.user.UserRestResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users") // http:///localhost:8080/users
public class UserController {

    private final UserService userService;
    private final OrganizationService organizationService;
    private final ModelMapper MAPPER = new ModelMapper();

    @Autowired
    public UserController(UserService userService,
                          OrganizationService organizationService) {
        this.userService = userService;
        this.organizationService = organizationService;
    }

    @PostMapping("/{organizationId}")
    public UserRestResponse saveUser(@RequestBody UserWithNoOrganizationRequest user,
                                     @PathVariable(name = "organizationId") String organizationId) {
        if (user.getPassword().length() < 6) {
            throw new RuntimeException("User password should be 6 characters and longer");
        }

        OrganizationDto organizationDto = organizationService.findByOrganizationId(organizationId);
        UserDto userDto = new UserDto();
        BeanUtils.copyProperties(user,userDto);

        userDto.setOrganization(organizationDto);

        UserDto createdUser = userService.createUser(userDto);
        UserRestResponse returnedValue = MAPPER.map(createdUser, UserRestResponse.class);

        return returnedValue;

    }


    @GetMapping("/{userId}")
    public UserRestResponse getUser(@PathVariable(name = "userId") String userId) {
        UserDto userDto = userService.getUserByUserId(userId);
        UserRestResponse returnedValue = MAPPER.map(userDto, UserRestResponse.class);

        return returnedValue;
    }

    @PostMapping("/organizations")
    public UserRestResponse createUserAndOrganization(@RequestBody UserDetailsRequestModel userDetails) {
        if (userDetails.getPassword().length() < 6) {
            throw new RuntimeException("User password should be 6 characters and longer");
        }

        OrganizationDetailsRequestModel organization = userDetails.getOrganization();
        OrganizationDto organizationDto = MAPPER.map(organization, OrganizationDto.class);
        OrganizationDto savedOrganization = organizationService.createNewOrganization(organizationDto);

        UserDto userDto = MAPPER.map(userDetails, UserDto.class);
        userDto.setOrganization(savedOrganization);
        UserDto createdUser = userService.createUser(userDto);

        UserRestResponse returnedValue = MAPPER.map(createdUser, UserRestResponse.class);

        return returnedValue;
    }

    @PutMapping({"{userId}"})
    public UserRestResponse updateUser(@RequestBody UserDetailsRequestModel userDetails,
                             @PathVariable(name = "userId") String userId) {
        return null;
    }

    @DeleteMapping
    public String deleteUser() {

        return "deleteUser";
    }

}
