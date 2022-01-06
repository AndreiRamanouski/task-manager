package by.htp.ramanouski.taskmanager.service.impl;

import by.htp.ramanouski.taskmanager.dto.UserDto;
import by.htp.ramanouski.taskmanager.entity.OrganizationEntity;
import by.htp.ramanouski.taskmanager.entity.UserEntity;
import by.htp.ramanouski.taskmanager.repository.UserRepository;
import by.htp.ramanouski.taskmanager.service.OrganizationService;
import by.htp.ramanouski.taskmanager.service.ServiceUtils;
import by.htp.ramanouski.taskmanager.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final OrganizationService organizationService;
    private final ServiceUtils serviceUtils;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           OrganizationService organizationService,
                           ServiceUtils serviceUtils) {
        this.userRepository = userRepository;
        this.organizationService = organizationService;
        this.serviceUtils = serviceUtils;
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        if (userRepository.findUserByEmail(userDto.getEmail()) != null) {
            throw new RuntimeException("User with this email already exists");
        }
        String organizationNameDto = userDto.getOrganization().getOrganizationName();
        UserEntity userEntity = new UserEntity();
        BeanUtils.copyProperties(userDto, userEntity);

        userEntity.setUserId(serviceUtils.generatePublicUserId());
        userEntity.setEncryptedPassword("test");

        OrganizationEntity organization = organizationService
                .findByOrganizationName(organizationNameDto);

        if (organization == null) {
            organization = new OrganizationEntity();
            organization.setOrganizationName(organizationNameDto);

            organization.setOrganizationId(serviceUtils.generatePublicOrganizationId());
            userEntity.setOrganization(organization);
            organization.setUsers(new ArrayList<>());
        } else {
            organization.getUsers().add(userEntity);
        }
        userEntity.setOrganization(organization);
        organizationService.save(organization);

        UserEntity savedUser = userRepository.save(userEntity);

        ModelMapper mapper = new ModelMapper();
        UserDto returnedValue = mapper.map(savedUser, UserDto.class);

        return returnedValue;
    }
}
