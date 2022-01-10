package by.htp.ramanouski.taskmanager.service.impl;

import by.htp.ramanouski.taskmanager.dto.UserDto;
import by.htp.ramanouski.taskmanager.entity.UserEntity;
import by.htp.ramanouski.taskmanager.repository.UserRepository;
import by.htp.ramanouski.taskmanager.service.AddressService;
import by.htp.ramanouski.taskmanager.service.OrganizationService;
import by.htp.ramanouski.taskmanager.service.ServiceUtils;
import by.htp.ramanouski.taskmanager.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ServiceUtils serviceUtils;
    private final static ModelMapper MAPPER = new ModelMapper();

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           ServiceUtils serviceUtils) {
        this.userRepository = userRepository;
        this.serviceUtils = serviceUtils;
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        if (userRepository.findUserByEmail(userDto.getEmail()) != null) {
            throw new RuntimeException("User with this email already exists");
        }
        UserEntity userEntity = MAPPER.map(userDto,UserEntity.class);
        userEntity.setUserId(serviceUtils.generatePublicUserId());
        userEntity.setEncryptedPassword("test");

        UserEntity savedUser = userRepository.save(userEntity);
        UserDto returnedValue = MAPPER.map(savedUser, UserDto.class);
        return returnedValue;
    }

    @Override
    public UserDto getUserByUserId(String userId) {
        UserEntity userEntity = userRepository.findUserByUserId(userId);
        if(userEntity==null){
            throw new RuntimeException("There is no such user");
        }
        UserDto returnedValue = MAPPER.map(userEntity,UserDto.class);
        return returnedValue;
    }


}
