package by.htp.ramanouski.taskmanager.ui.model.response;


import by.htp.ramanouski.taskmanager.dto.OrganizationDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserRestResponse {
    private String userId;
    private String userName;
    private OrganizationDto organization;
    private String email;
}
