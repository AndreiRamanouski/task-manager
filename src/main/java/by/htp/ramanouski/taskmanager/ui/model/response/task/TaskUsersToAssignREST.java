package by.htp.ramanouski.taskmanager.ui.model.response.task;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskUsersToAssignREST {
    private Long id;
    private String userId;
    private String email;
    private String userName;
    private String lastName;
}
