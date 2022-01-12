package by.htp.ramanouski.taskmanager.service;

import by.htp.ramanouski.taskmanager.dto.UserDto;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserDto createNewUser(UserDto userDto);
    UserDto getUserByUserId(String userId);

    UserDto createNewAdminUser(UserDto userDto);
}
