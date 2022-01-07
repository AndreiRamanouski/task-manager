package by.htp.ramanouski.taskmanager.service.impl;

import by.htp.ramanouski.taskmanager.dto.AddressDto;
import by.htp.ramanouski.taskmanager.dto.UserDto;
import by.htp.ramanouski.taskmanager.entity.AddressEntity;
import by.htp.ramanouski.taskmanager.entity.OrganizationEntity;
import by.htp.ramanouski.taskmanager.entity.UserEntity;
import by.htp.ramanouski.taskmanager.repository.UserRepository;
import by.htp.ramanouski.taskmanager.service.AddressService;
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
    private final AddressService addressService;
    private final ServiceUtils serviceUtils;
    private final static ModelMapper MAPPER = new ModelMapper();

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           OrganizationService organizationService,
                           ServiceUtils serviceUtils,
                           AddressService addressService) {
        this.userRepository = userRepository;
        this.organizationService = organizationService;
        this.addressService = addressService;
        this.serviceUtils = serviceUtils;
    }

    @Override
    public UserDto createUser(UserDto userDto) {
        if (userRepository.findUserByEmail(userDto.getEmail()) != null) {
            throw new RuntimeException("User with this email already exists");
        }

        String organizationNameDto = userDto.getOrganization().getOrganizationName();
        UserEntity userEntity = MAPPER.map(userDto,UserEntity.class);

        userEntity.setUserId(serviceUtils.generatePublicUserId());
        userEntity.setEncryptedPassword("test");

        userEntity.getAddress().setAddressId(serviceUtils.generatePublicAddressId());

        OrganizationEntity organization = organizationService
                .findByOrganizationName(organizationNameDto);

        if (organization == null) {
            organization = createNewOrganization(organizationNameDto);
        } else {
            organization.getUsers().add(userEntity);
        }
        userEntity.setOrganization(organization);
        organizationService.save(organization);
        userEntity.getAddress().setAddressUser(userEntity);

        UserEntity savedUser = userRepository.save(userEntity);

        UserDto returnedValue = MAPPER.map(savedUser, UserDto.class);

        return returnedValue;
    }


    private OrganizationEntity createNewOrganization(String organizationName){
        OrganizationEntity returnedValue = new OrganizationEntity();
        returnedValue.setOrganizationName(organizationName);
        returnedValue.setOrganizationId(serviceUtils.generatePublicOrganizationId());
        returnedValue.setUsers(new ArrayList<>());
        return returnedValue;
    }
}
