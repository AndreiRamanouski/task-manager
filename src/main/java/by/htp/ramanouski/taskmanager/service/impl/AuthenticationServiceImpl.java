package by.htp.ramanouski.taskmanager.service.impl;

import by.htp.ramanouski.taskmanager.dto.UserDto;
import by.htp.ramanouski.taskmanager.entity.UserEntity;
import by.htp.ramanouski.taskmanager.security.SecurityConstants;
import by.htp.ramanouski.taskmanager.service.AuthenticationService;
import by.htp.ramanouski.taskmanager.service.UserService;
import by.htp.ramanouski.taskmanager.ui.model.request.UserLoginRequestModel;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final UserService userService;


    public AuthenticationServiceImpl(AuthenticationManager authenticationManager, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
    }

    @Override
    public Map<String, String> loginAttempt(UserLoginRequestModel userLoginRequestModel) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userLoginRequestModel.getEmail(),
                        userLoginRequestModel.getPassword()
                )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String userName = authentication.getName();

        String token = Jwts.builder()
                .setSubject(userName)
                .setExpiration(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SecurityConstants.TOKEN_SECRET)
                .compact();

        UserDto userDto = userService.findUserByEmail(userName);
        String userId = userDto.getUserId();
        String organizationId = userDto.getOrganization().getOrganizationId();

        Map<String, String> returnedValue = new HashMap<>();


        returnedValue.put(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX + token);
        returnedValue.put("userID", userId);
        returnedValue.put("organizationID", organizationId);
        return returnedValue;
    }
}
