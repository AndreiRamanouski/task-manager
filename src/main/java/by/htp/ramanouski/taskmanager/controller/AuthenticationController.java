package by.htp.ramanouski.taskmanager.controller;

import by.htp.ramanouski.taskmanager.service.AuthenticationService;
import by.htp.ramanouski.taskmanager.ui.model.request.UserLoginRequestModel;
import by.htp.ramanouski.taskmanager.ui.model.response.UserAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;


@RestController
@CrossOrigin
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody UserLoginRequestModel userLoginRequestModel) {
        Map<String, String> userHeaders = authenticationService.loginAttempt(userLoginRequestModel);
        HttpHeaders responseHeaders = new HttpHeaders();
        String token = userHeaders.get("Authorization");
        for (Map.Entry<String, String> header : userHeaders.entrySet()) {
            responseHeaders.add(header.getKey(), header.getValue());
        }

        return ResponseEntity.ok()
                .headers(responseHeaders)
                .body(token);
    }
}
