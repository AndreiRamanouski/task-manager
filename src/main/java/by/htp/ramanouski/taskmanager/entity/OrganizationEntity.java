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

    @OneToMany(mappedBy = "organization")
    private List<UserEntity> users;
}
