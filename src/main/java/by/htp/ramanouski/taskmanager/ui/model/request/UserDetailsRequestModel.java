package by.htp.ramanouski.taskmanager.ui.model.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsRequestModel {
    private Long id;
    private String userName;
    private String lastName;
    private String email;
    private String password;
    private OrganizationDetailsRequestModel organization;
}
