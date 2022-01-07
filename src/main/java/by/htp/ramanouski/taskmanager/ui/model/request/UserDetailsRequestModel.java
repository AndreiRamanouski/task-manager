package by.htp.ramanouski.taskmanager.ui.model.request;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDetailsRequestModel {
    private String organization;
    private String phoneNumber;
    private AddressDetailsRequestModel address;
    private String userName;
    private String email;
    private String password;
}
