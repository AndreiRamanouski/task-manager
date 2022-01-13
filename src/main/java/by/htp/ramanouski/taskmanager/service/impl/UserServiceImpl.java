package by.htp.ramanouski.taskmanager.service.impl;

import by.htp.ramanouski.taskmanager.dto.UserDto;
import by.htp.ramanouski.taskmanager.entity.RoleEntity;
import by.htp.ramanouski.taskmanager.entity.UserEntity;
import by.htp.ramanouski.taskmanager.repository.RoleRepository;
import by.htp.ramanouski.taskmanager.repository.UserRepository;
import by.htp.ramanouski.taskmanager.service.exception.ServiceException;
import by.htp.ramanouski.taskmanager.service.utils.ServiceUtils;
import by.htp.ramanouski.taskmanager.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ServiceUtils serviceUtils;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final RoleRepository roleRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository,
                           ServiceUtils serviceUtils, BCryptPasswordEncoder bCryptPasswordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.serviceUtils = serviceUtils;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.roleRepository = roleRepository;
    }

    private UserDto createUser(UserDto userDto, List<String> roles) {
        if (userRepository.findUserByEmail(userDto.getEmail()) != null) {
            throw new ServiceException("User with this email already exists");
        }

        ModelMapper mapper = new ModelMapper();
        UserEntity userEntity = mapper.map(userDto,UserEntity.class);

        userEntity.setUserId(serviceUtils.generatePublicUserId());
        userEntity.setEncryptedPassword(bCryptPasswordEncoder.encode(userDto.getPassword()));

        List<RoleEntity> roleEntities = new ArrayList<>();
        roles.forEach(
                (role) -> {
                    //этот блок if только для первоначальной инициализации
                    //можно было через EventListener сделать при ApplicationReadyEvent
                    //но было удобнее для понимания так. Все равно это надо удалять потом
                    if(roleRepository.findByName(role) == null){
                        roleRepository.save(RoleEntity.builder().name(role).build());
                    }
                    RoleEntity roleEntity = roleRepository.findByName(role);
                    roleEntities.add(roleEntity);
                    if (roleEntity.getUsers() == null){
                        roleEntity.setUsers(new ArrayList<>());
                    }
                    roleEntity.getUsers().add(userEntity);
                }
        );
        userEntity.setRoles(roleEntities);
        UserEntity savedUser = userRepository.save(userEntity);
        UserDto returnedValue = mapper.map(savedUser, UserDto.class);
        return returnedValue;
    }

    @Override
    public UserDto createNewUser(UserDto userDto) {
        UserDto returnedValue = createUser(userDto, List.of("ROLE_USER"));
        return returnedValue;
    }

    @Override
    public UserDto createNewAdminUser(UserDto userDto) {
        UserDto returnedValue = createUser(userDto, List.of("ROLE_ADMIN", "ROLE_USER"));
        return returnedValue;
    }

    @Override
    public UserDto getUserByUserId(String userId) {
        UserEntity userEntity = userRepository.findUserByUserId(userId);
        if(userEntity==null){
            throw new ServiceException("There is no such user");
        }
        ModelMapper mapper = new ModelMapper();
        UserDto returnedValue = mapper.map(userEntity,UserDto.class);
        return returnedValue;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity userByEmail = userRepository.findUserByEmail(email);

        if(userByEmail == null){
            throw new UsernameNotFoundException("No user with email: " + email);
        }

        return new User(userByEmail.getEmail(), userByEmail.getEncryptedPassword(), userByEmail.getRoles());
    }
}
