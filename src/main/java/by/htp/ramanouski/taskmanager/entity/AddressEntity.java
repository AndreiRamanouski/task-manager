package by.htp.ramanouski.taskmanager.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Builder
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddressId() {
        return addressId;
    }

    public void setAddressId(String addressId) {
        this.addressId = addressId;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getStreetName() {
        return streetName;
    }

    public void setStreetName(String streetName) {
        this.streetName = streetName;
    }

    public OrganizationEntity getAddressOrganization() {
        return addressOrganization;
    }

    public void setAddressOrganization(OrganizationEntity addressOrganization) {
        this.addressOrganization = addressOrganization;
    }

}
