package by.htp.ramanouski.taskmanager.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "organizations")
public class OrganizationEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String organizationId;

    @Column(nullable = false)
    private String organizationName;

    @Column(nullable = false)
    private String defaultPassword;

    @Column(length = 30)
    private String phoneNumber;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "address_id", nullable = false)
    private AddressEntity address;

    @OneToMany(mappedBy = "organization", fetch = FetchType.LAZY)
    private List<UserEntity> users;
}
