package by.htp.ramanouski.taskmanager.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressDto {
    private Long id;
    private String addressId;
    private String countryName;
    private String streetName;
    private UserDto addressUser;
}
