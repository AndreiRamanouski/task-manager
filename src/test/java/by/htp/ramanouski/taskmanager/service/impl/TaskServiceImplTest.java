package by.htp.ramanouski.taskmanager.service.impl;

import by.htp.ramanouski.taskmanager.dto.TaskDto;
import by.htp.ramanouski.taskmanager.entity.TaskEntity;
import by.htp.ramanouski.taskmanager.entity.UserEntity;
import by.htp.ramanouski.taskmanager.repository.TaskRepository;
import by.htp.ramanouski.taskmanager.repository.UserRepository;
import by.htp.ramanouski.taskmanager.service.exception.ServiceException;
import by.htp.ramanouski.taskmanager.service.utils.ServiceUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class TaskServiceImplTest {

    @InjectMocks
    private TaskServiceImpl taskServiceImpl;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ServiceUtils serviceUtils;


    private TaskEntity taskEntity;
    private UserEntity userEntity;


    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        userEntity = createUserEntityTests();
        taskEntity = TaskEntity.builder().title("Mock Title")
                .targetDate(LocalDate.now()).isFinished(false)
                .taskId(serviceUtils.generatePublicTaskId())
                .build();

    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void findTaskByTaskId() {
        when(taskRepository.findTaskByTaskId(anyString())).thenReturn(taskEntity);
        TaskDto taskDto = taskServiceImpl.findTaskByTaskId("12345");
        assertNotNull(taskDto);
        assertEquals(taskEntity.getTaskId(), taskDto.getTaskId());
    }

    @Test
    void findTaskByTaskId_ThrowsServiceException() {
        when(taskRepository.findTaskByTaskId(anyString())).thenReturn(null);
        assertThrows(ServiceException.class, () -> {
            taskServiceImpl.findTaskByTaskId("ddd");
        });
    }

    @Test
    void saveNewTask() {
        when(userRepository.findUserByUserId(anyString())).thenReturn(userEntity);
        when(taskRepository.save(any(TaskEntity.class))).thenReturn(taskEntity);

        TaskDto taskDto = TaskDto.builder()
                .taskId(serviceUtils.generatePublicTaskId())
                .isFinished(false)
                .targetDate(LocalDate.now())
                .build();

        TaskDto savedTask = taskServiceImpl
                .saveNewTask(taskDto, List.of("mock"), "mockUserId");

        assertNotNull(savedTask);
        assertEquals(taskEntity.getTaskId(), savedTask.getTaskId());
    }


    private UserEntity createUserEntityTests() {
        return UserEntity.builder()
                .userId(serviceUtils.generatePublicUserId())
                .userName("Petr").lastName("Petrov")
                .email("petrov@mail.ru")
                .tasks(new ArrayList<>())
                .build();
    }
}