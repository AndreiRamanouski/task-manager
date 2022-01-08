package by.htp.ramanouski.taskmanager.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
    private Long id;
    private String userId;
    private OrganizationDto organization;
    private String userName;
    private String lastName;
    private String email;
    private String password;
    private String encryptedPassword;
}
