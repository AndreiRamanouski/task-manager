package by.htp.ramanouski.taskmanager.ui.controller.utils;

import by.htp.ramanouski.taskmanager.dto.OrganizationDto;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class ControllerUtils {

    public List<String> getAllUsersIdInOrganization(OrganizationDto organizationDto) {
        List<String> returnedValue = new ArrayList<>();
        if (organizationDto.getUsers() == null) {
            return Collections.emptyList();
        }
        organizationDto.getUsers().forEach((user) -> {
            returnedValue.add(user.getUserId());
        });
        return returnedValue;
    }
}
