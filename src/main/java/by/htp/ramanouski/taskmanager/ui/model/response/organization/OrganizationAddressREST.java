package by.htp.ramanouski.taskmanager.ui.model.response.organization;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationAddressREST {
    private Long id;
    private String addressId;
    private String countryName;
    private String streetName;
}
