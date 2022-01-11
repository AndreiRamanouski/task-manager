package by.htp.ramanouski.taskmanager.ui.model.response.organization;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationRestResponse {
    private String organizationId;
    private String organizationName;
    private String phoneNumber;
    private OrganizationAddressREST address;
    private List<OrganizationUserREST> users;
}
