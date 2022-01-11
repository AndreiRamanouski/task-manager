package by.htp.ramanouski.taskmanager.ui.model.response.task;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor@NoArgsConstructor
public class TaskUserREST {
    private Long id;
    private String userId;
    private String email;
    private String userName;
    private String lastName;
}
