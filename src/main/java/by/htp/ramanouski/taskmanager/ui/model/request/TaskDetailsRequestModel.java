package by.htp.ramanouski.taskmanager.ui.model.request;

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
public class TaskDetailsRequestModel {
    private Long id;
    private String taskId;
    private String title;
    private LocalDate targetDate;
    public List<String> usersPublicIdToAssign;
}
