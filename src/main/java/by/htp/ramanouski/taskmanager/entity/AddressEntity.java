package by.htp.ramanouski.taskmanager.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "addresses")
public class AddressEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String addressId;

    @Column(nullable = false)
    private String countryName;

    @Column
    private String streetName;

    @OneToOne(mappedBy = "address")
    private OrganizationEntity addressOrganization;
}
