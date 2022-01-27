package by.htp.ramanouski.taskmanager.ui.model.request;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsRequestModel {
    private String userId;
    private String userName;
    private String lastName;
    private String email;
    private String password;
    private OrganizationDetailsRequestModel organization;
}
