package by.htp.ramanouski.taskmanager.ui.model.response.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserOrganizationREST {
    private Long id;
    private String organizationId;
    private String organizationName;
    private String phoneNumber;
    private UserAddressREST address;
}
