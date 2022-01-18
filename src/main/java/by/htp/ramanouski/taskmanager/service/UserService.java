package by.htp.ramanouski.taskmanager.service;

import by.htp.ramanouski.taskmanager.dto.UserDto;
import by.htp.ramanouski.taskmanager.entity.UserEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    UserDto createNewUser(UserDto userDto);
    UserDto getUserByUserId(String userId);
    UserDto createNewAdminUser(UserDto userDto);
    UserEntity findUserByEmail(String email);
}
