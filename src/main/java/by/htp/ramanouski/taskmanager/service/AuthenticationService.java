package by.htp.ramanouski.taskmanager.service;

import by.htp.ramanouski.taskmanager.ui.model.request.UserLoginRequestModel;
import java.util.Map;


public interface AuthenticationService {
    Map<String, String> loginAttempt(UserLoginRequestModel userLoginRequestModel);
}
