package by.htp.ramanouski.taskmanager.dto;

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
public class TaskDto {
    private Long id;
    private String taskId;
    private String title;
    private LocalDate targetDate;
    private boolean isFinished;
    private List<UserDto> users;
}
