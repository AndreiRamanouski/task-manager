package by.htp.ramanouski.taskmanager.ui.model.response.organization;

import by.htp.ramanouski.taskmanager.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationRestResponse {
    private String organizationId;
    private String organizationName;
    private List<OrganizationUserREST> users;
}
