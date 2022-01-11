package by.htp.ramanouski.taskmanager.ui.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserWithNoOrganizationRequest {
    private String userName;
    private String lastName;
    private String email;
}
