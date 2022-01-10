package by.htp.ramanouski.taskmanager.ui.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrganizationDetailsRequestModel {
    private String organizationName;
    private String phoneNumber;
    private String defaultPassword;
    private AddressDetailsRequestModel address;
}
