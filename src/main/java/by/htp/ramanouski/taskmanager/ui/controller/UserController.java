package by.htp.ramanouski.taskmanager.ui.controller;


import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users") // http:///localhost:8080/users
public class UserController {

    @GetMapping
    public String getUser(){

        return "getUser";
    }

    @PostMapping
    public String createUser(){

        return "createUser";
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
