package by.htp.ramanouski.taskmanager.ui.model.response.organization;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrganizationUserREST {
    private Long id;
    private String userId;
    private String userName;
    private String lastName;
    private String email;
}
