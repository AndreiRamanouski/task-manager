package by.htp.ramanouski.taskmanager.service.impl;

import by.htp.ramanouski.taskmanager.dto.TaskDto;
import by.htp.ramanouski.taskmanager.email.service.EmailSenderService;
import by.htp.ramanouski.taskmanager.entity.OrganizationEntity;
import by.htp.ramanouski.taskmanager.entity.TaskEntity;
import by.htp.ramanouski.taskmanager.entity.UserEntity;
import by.htp.ramanouski.taskmanager.repository.OrganizationRepository;
import by.htp.ramanouski.taskmanager.repository.TaskRepository;
import by.htp.ramanouski.taskmanager.repository.UserRepository;
import by.htp.ramanouski.taskmanager.service.TaskService;
import by.htp.ramanouski.taskmanager.service.exception.ServiceException;
import by.htp.ramanouski.taskmanager.service.utils.ServiceUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final OrganizationRepository organizationRepository;
    private final EmailSenderService emailSenderService;
    private final ServiceUtils serviceUtils;

    @Autowired
    public TaskServiceImpl(TaskRepository taskRepository, UserRepository userRepository, OrganizationRepository organizationRepository, EmailSenderService emailSenderService, ServiceUtils serviceUtils) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.organizationRepository = organizationRepository;
        this.emailSenderService = emailSenderService;
        this.serviceUtils = serviceUtils;
    }


    @Override
    public TaskDto findTaskByTaskId(String taskId) {
        TaskEntity taskEntity = taskRepository.findTaskByTaskId(taskId);
        if (taskEntity == null) {
            throw new ServiceException("There is no task with id " + taskId);
        }
        ModelMapper mapper = new ModelMapper();
        TaskDto returnedValue = mapper.map(taskEntity, TaskDto.class);
        return returnedValue;
    }

    @Override
    public TaskDto saveNewTask(String organizationId, TaskDto taskDto,
                               List<String> usersPublicIdToAssign, String userId) {
        ModelMapper mapper = new ModelMapper();
        TaskEntity taskEntity = mapper.map(taskDto, TaskEntity.class);

        taskEntity.setTaskId(serviceUtils.generatePublicTaskId());

        List<UserEntity> users = new ArrayList<>();
        usersPublicIdToAssign.forEach((userPublicId) -> {
            UserEntity userEntity = userRepository.findUserByUserId(userPublicId);
            if (userEntity.getTasks() == null){
                userEntity.setTasks(new ArrayList<>());
            }
            userEntity.getTasks().add(taskEntity);
            users.add(userEntity);
        });

        taskEntity.setUsers(users);
        TaskEntity savedTask = taskRepository.save(taskEntity);


        TaskDto returnedValue = mapper.map(savedTask, TaskDto.class);
        sendMessages(returnedValue, userId);
        return returnedValue;
    }

    private void sendMessages(TaskDto taskDto, String userId) {
        UserEntity userEntity = userRepository.findUserByUserId(userId);
        String userName;
        if (userEntity == null) {
            userName = "Somebody";
        } else {
            userName = userEntity.getUserName() + " " + userEntity.getLastName();
        }
        String messageTitle = "You have a new task" + taskDto.getTitle();
        String massageBody = userName + " assigned a task for you. Due date is " + taskDto.getTargetDate();
        List<String> emails = new ArrayList<>();
        taskDto.getUsers().forEach((user) -> {
            emails.add(user.getEmail());
        });
        emailSenderService.sendMessages(emails, messageTitle, massageBody);
    }
}
