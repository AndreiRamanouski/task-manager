package by.htp.ramanouski.taskmanager.ui.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrganizationDetailsRequestModel {
    private Long id;
    private String organizationId;
    private String organizationName;
    private String phoneNumber;
    private String defaultPassword;
    private AddressDetailsRequestModel address;
}
