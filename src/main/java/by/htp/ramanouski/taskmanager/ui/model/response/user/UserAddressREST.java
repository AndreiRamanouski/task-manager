package by.htp.ramanouski.taskmanager.ui.model.response.user;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserAddressREST {
    private Long id;
    private String addressId;
    private String countryName;
    private String streetName;
}
