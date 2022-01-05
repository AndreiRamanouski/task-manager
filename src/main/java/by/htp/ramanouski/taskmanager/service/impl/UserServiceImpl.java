package by.htp.ramanouski.taskmanager.service.impl;

import by.htp.ramanouski.taskmanager.dto.UserDto;
import by.htp.ramanouski.taskmanager.entity.OrganizationEntity;
import by.htp.ramanouski.taskmanager.entity.UserEntity;
import by.htp.ramanouski.taskmanager.repository.OrganizationRepository;
import by.htp.ramanouski.taskmanager.repository.UserRepository;
import by.htp.ramanouski.taskmanager.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final OrganizationRepository organizationRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           OrganizationRepository organizationRepository) {
        this.userRepository = userRepository;
        this.organizationRepository = organizationRepository;
    }

    @Override
    public UserDto createUser(UserDto userDto) {

        if(userRepository.findUserByEmail(userDto.getEmail()) != null){
            throw new RuntimeException("User with this email already exists");
        }

        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(userDto,userEntity);

        userEntity.setUserId("test");
        userEntity.setEncryptedPassword("test");

        OrganizationEntity organizationEntity = new OrganizationEntity();
        organizationEntity.setOrganizationId("test");
        organizationEntity.setOrganizationName("testOrganization");
        organizationRepository.save(organizationEntity);
        userEntity.setOrganization(organizationEntity);

        UserEntity savedUser =  userRepository.save(userEntity);

        ModelMapper mapper = new ModelMapper();
        UserDto returnedValue = mapper.map(savedUser,UserDto.class);

        return returnedValue;
    }
}
