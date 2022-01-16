package by.htp.ramanouski.taskmanager.ui.model.response.user;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserOrganizationREST {
    private Long id;
    private String organizationId;
    private String organizationName;
    private String phoneNumber;
}
