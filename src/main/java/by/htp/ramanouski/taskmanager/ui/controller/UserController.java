package by.htp.ramanouski.taskmanager.ui.controller;


import by.htp.ramanouski.taskmanager.dto.OrganizationDto;
import by.htp.ramanouski.taskmanager.dto.UserDto;
import by.htp.ramanouski.taskmanager.service.UserService;
import by.htp.ramanouski.taskmanager.ui.model.request.UserDetailsRequestModel;
import by.htp.ramanouski.taskmanager.ui.model.response.user.UserRestResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users") // http:///localhost:8080/users
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @GetMapping
    public String getUser(){

        return "getUser";
    }

    @PostMapping
    public UserRestResponse createUser(@RequestBody UserDetailsRequestModel userDetails){

        ModelMapper mapper = new ModelMapper();
        UserDto  userDto = mapper.map(userDetails,UserDto.class);

        userDto.setOrganization(
                OrganizationDto.builder()
                .organizationName(userDetails.getOrganization())
                .build()
        );

        UserDto created = userService.createUser(userDto);

        UserRestResponse returnedValue = mapper.map(created,UserRestResponse.class);

        return returnedValue;
    }

    @PutMapping
    public String updateUser(){

        return "updateUser";
    }

    @DeleteMapping
    public String deleteUser(){

        return "deleteUser";
    }

}
