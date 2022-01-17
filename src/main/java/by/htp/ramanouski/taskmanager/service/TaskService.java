package by.htp.ramanouski.taskmanager.service;

import by.htp.ramanouski.taskmanager.dto.TaskDto;
import by.htp.ramanouski.taskmanager.ui.model.request.TaskDetailsRequestModel;
import by.htp.ramanouski.taskmanager.ui.model.response.task.TaskRestResponse;

import java.util.List;

public interface TaskService {
    TaskDto findTaskByTaskId(String taskId);

    TaskDto saveNewTask(TaskDto taskDto, List<String> usersPublicIdToAssign, String userId);

    List<TaskDto> findAllWhereOrganizationId(String organizationId);
}
