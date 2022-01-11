package by.htp.ramanouski.taskmanager.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrganizationDto {
    private Long id;
    private String organizationId;
    private String organizationName;
    private String phoneNumber;
    private String defaultPassword;
    private AddressDto address;
    private List<UserDto> users;
}
