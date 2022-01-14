package by.htp.ramanouski.taskmanager.controller.utils;

import by.htp.ramanouski.taskmanager.dto.OrganizationDto;
import by.htp.ramanouski.taskmanager.ui.model.response.task.TaskUsersToAssignREST;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class ControllerUtils {

    public List<TaskUsersToAssignREST> getAllUsersIdInOrganization(OrganizationDto organizationDto) {
        List<TaskUsersToAssignREST> returnedValue = new ArrayList<>();
        ModelMapper mapper = new ModelMapper();
        if (organizationDto.getUsers() == null) {
            return Collections.emptyList();
        }
        organizationDto.getUsers().forEach((user) -> {
            returnedValue.add(mapper.map(user,TaskUsersToAssignREST.class));
        });
        return returnedValue;
    }
}
