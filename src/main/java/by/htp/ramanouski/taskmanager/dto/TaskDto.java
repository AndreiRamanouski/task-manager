package by.htp.ramanouski.taskmanager.dto;

import by.htp.ramanouski.taskmanager.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskDto {
    private Long id;
    private String taskId;
    private String title;
    private boolean isFinished;
    private List<UserEntity> users;
}
