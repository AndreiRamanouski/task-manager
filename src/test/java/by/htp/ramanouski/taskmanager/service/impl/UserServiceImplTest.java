package by.htp.ramanouski.taskmanager.service.impl;

import by.htp.ramanouski.taskmanager.dto.AddressDto;
import by.htp.ramanouski.taskmanager.dto.OrganizationDto;
import by.htp.ramanouski.taskmanager.dto.UserDto;
import by.htp.ramanouski.taskmanager.entity.AddressEntity;
import by.htp.ramanouski.taskmanager.entity.OrganizationEntity;
import by.htp.ramanouski.taskmanager.entity.RoleEntity;
import by.htp.ramanouski.taskmanager.entity.UserEntity;
import by.htp.ramanouski.taskmanager.repository.RoleRepository;
import by.htp.ramanouski.taskmanager.repository.UserRepository;
import by.htp.ramanouski.taskmanager.service.UserService;
import by.htp.ramanouski.taskmanager.service.exception.ServiceException;
import by.htp.ramanouski.taskmanager.service.utils.ServiceUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

class UserServiceImplTest {
    @InjectMocks
    private UserServiceImpl userServiceImpl;

    @Mock
    private ServiceUtils serviceUtils;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Mock
    private UserRepository userRepository;

    @Mock
    private RoleRepository roleRepository;

    private RoleEntity roleEntity;
    private UserEntity userEntity;
    private UserDto userDtoMock;

    String encryptedPassword = "sadvabr335";


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        userEntity = createMockUserEntity();
        userDtoMock = createMockUserDto();
        roleEntity = RoleEntity.builder().name("MOCK_ROLE").build();
    }

    @AfterEach
    void tearDown() {
    }


    @Test
    void createNewAdminUser() {
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);

        when(roleRepository.findByName(anyString())).thenReturn(roleEntity);
        when(bCryptPasswordEncoder.encode(anyString())).thenReturn(encryptedPassword);
        userDtoMock.setPassword("sadvabr335");
        UserDto userDto = userServiceImpl.createNewAdminUser(userDtoMock);

        assertNotNull(userDto);
        assertEquals(userDtoMock.getUserId(), userDto.getUserId());

        verify(bCryptPasswordEncoder, times(1)).encode("sadvabr335");

    }

    @Test
    void createNewUser() {
        when(userRepository.save(any(UserEntity.class))).thenReturn(userEntity);
        when(roleRepository.findByName(anyString())).thenReturn(roleEntity);
        UserDto userDto = userServiceImpl.createNewAdminUser(userDtoMock);
        assertNotNull(userDto);
        assertEquals(userDtoMock.getUserId(), userDto.getUserId());
        assertEquals(userDtoMock.getEmail(), userDto.getEmail());
    }

    @Test
    void createNewUser_ThrowsServiceException() {
        when(userRepository.findUserByEmail(anyString())).thenReturn(userEntity);
        assertThrows(ServiceException.class, () -> {
            userServiceImpl.createNewUser(UserDto.builder().userName("MockName")
                    .password("password")
                    .email("email@gmail.com")
                    .build());
        });
    }

    @Test
    void getUserByUserId() {
        when(userRepository.findUserByUserId(anyString())).thenReturn(userEntity);
        UserDto userDto = userServiceImpl.getUserByUserId("test@test.com");
        assertNotNull(userDto);
        assertEquals("Mock", userDto.getUserName());
    }

    @Test
    void getUserByUserId_ThrowsServiceException() {
        when(userRepository.findUserByUserId(anyString())).thenReturn(null);
        assertThrows(ServiceException.class, () -> {
            userServiceImpl.getUserByUserId("asd");
        });
    }

    @Test
    void loadUserByUsername() {
        when(userRepository.findUserByEmail(anyString())).thenReturn(userEntity);
        UserDetails user = userServiceImpl.loadUserByUsername("mock@mail.ru");
        assertNotNull(user);
        assertEquals("mock@mail.ru", user.getUsername());
    }

    @Test
    void loadUserByUsername_ThrowsUsernameNotFoundException() {
        when(userRepository.findUserByEmail(anyString())).thenReturn(null);
        assertThrows(UsernameNotFoundException.class, ()->{
            userServiceImpl.loadUserByUsername("mock@mail.ru");
        });
    }

    private UserEntity createMockUserEntity() {
        return UserEntity.builder()
                .userName("Mock").lastName("MockLast")
                .email("mock@mail.ru")
                .encryptedPassword("mock")
                .roles(new ArrayList<>())
                .organization(
                        OrganizationEntity.builder()
                                .organizationName("MockOrganization")
                                .defaultPassword("default")
                                .phoneNumber("123-45-67")
                                .organizationId(serviceUtils.generatePublicOrganizationId())
                                .address(
                                        AddressEntity.builder()
                                                .countryName("MockCountry")
                                                .streetName("MockStreet")
                                                .addressId(serviceUtils.generatePublicAddressId())
                                                .build()
                                ).build()
                ).build();
    }

    private UserDto createMockUserDto() {
        return UserDto.builder()
                .userName("Mock").lastName("MockLast")
                .email("mock@mail.ru")
                .organization(
                        OrganizationDto.builder()
                                .organizationName("MockOrganization")
                                .defaultPassword("default")
                                .phoneNumber("123-45-67")
                                .organizationId(serviceUtils.generatePublicOrganizationId())
                                .address(
                                        AddressDto.builder()
                                                .countryName("MockCountry")
                                                .streetName("MockStreet")
                                                .addressId(serviceUtils.generatePublicAddressId())
                                                .build()
                                ).build()
                ).build();
    }

}