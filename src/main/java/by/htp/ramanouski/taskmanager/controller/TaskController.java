package by.htp.ramanouski.taskmanager.controller;

import by.htp.ramanouski.taskmanager.dto.OrganizationDto;
import by.htp.ramanouski.taskmanager.dto.TaskDto;
import by.htp.ramanouski.taskmanager.service.OrganizationService;
import by.htp.ramanouski.taskmanager.service.TaskService;
import by.htp.ramanouski.taskmanager.controller.utils.ControllerUtils;
import by.htp.ramanouski.taskmanager.ui.model.request.TaskDetailsRequestModel;
import by.htp.ramanouski.taskmanager.ui.model.response.task.TaskRestResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class TaskController {
    private final OrganizationService organizationService;
    private final TaskService taskService;
    private final ControllerUtils controllerUtils;

    @Autowired
    public TaskController(OrganizationService organizationService,
                          TaskService taskService, ControllerUtils controllerUtils) {
        this.organizationService = organizationService;
        this.taskService = taskService;
        this.controllerUtils = controllerUtils;
    }

    @GetMapping("organizations/{organizationId}/tasks/{taskId}")
    private TaskRestResponse getTask(@PathVariable(name = "organizationId") String organizationId,
                                     @PathVariable(name = "taskId") String taskId) {
        TaskDto taskDto = taskService.findTaskByTaskId(taskId);
        OrganizationDto organizationDto = organizationService.findByOrganizationId(organizationId);

        ModelMapper mapper = new ModelMapper();
        TaskRestResponse returnedValue = mapper.map(taskDto, TaskRestResponse.class);
        returnedValue.setAllUsersInTheOrganization(controllerUtils.getAllUsersIdInOrganization(organizationDto));

        return returnedValue;
    }

    @GetMapping("organizations/{organizationId}/tasks")
    private TaskRestResponse createTask(@PathVariable(name = "organizationId") String organizationId) {
        OrganizationDto organizationDto = organizationService.findByOrganizationId(organizationId);
        TaskRestResponse returnedValue = TaskRestResponse.builder()
                .allUsersInTheOrganization(controllerUtils.getAllUsersIdInOrganization(organizationDto))
                .build();
        return returnedValue;
    }

    @PostMapping("/organizations/{organizationId}/users/{userId}/tasks")
    private TaskRestResponse createTask(@RequestBody TaskDetailsRequestModel requestModel,
                                        @PathVariable(name = "organizationId") String organizationId,
                                        @PathVariable(name = "userId") String userId) {
        OrganizationDto organizationDto = organizationService.findByOrganizationId(organizationId);

        List<String> usersPublicIdToAssign = requestModel.getUsersPublicIdToAssign();
        ModelMapper mapper = new ModelMapper();
        TaskDto taskToSave = mapper.map(requestModel, TaskDto.class);

        TaskDto taskDto = taskService.saveNewTask(organizationId, taskToSave, usersPublicIdToAssign, userId);

        TaskRestResponse returnedValue = mapper.map(taskDto, TaskRestResponse.class);
        returnedValue.setAllUsersInTheOrganization(controllerUtils.getAllUsersIdInOrganization(organizationDto));

        return returnedValue;
    }


}
