package by.htp.ramanouski.taskmanager.ui.model.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDetailsRequestModel {
    private String countryName;
    private String streetName;
}
