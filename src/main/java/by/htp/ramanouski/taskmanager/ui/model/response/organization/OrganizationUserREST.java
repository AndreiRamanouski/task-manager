package by.htp.ramanouski.taskmanager.ui.model.response.organization;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrganizationUserREST {
    private String userName;
    private String email;
}
