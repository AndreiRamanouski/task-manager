package by.htp.ramanouski.taskmanager.ui.model.response.user;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRestResponse {
    private String userId;
    private String userName;
    private UserOrganizationREST organization;
    private String email;
}
