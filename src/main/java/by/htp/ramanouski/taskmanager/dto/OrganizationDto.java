package by.htp.ramanouski.taskmanager.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrganizationDto {
    private Long id;
    private String organizationId;
    private String organizationName;
    private List<UserDto> users;
}
