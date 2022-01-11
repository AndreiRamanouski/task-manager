package by.htp.ramanouski.taskmanager.ui.model.response.user;

import by.htp.ramanouski.taskmanager.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserTaskREST {
    private Long id;
    private String taskId;
    private String title;
    private LocalDate targetDate;
    private boolean isFinished;
}
