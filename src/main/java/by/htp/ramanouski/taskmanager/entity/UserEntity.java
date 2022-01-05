package by.htp.ramanouski.taskmanager.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class UserEntity {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, length = 30)
    private String userId;

    @Column(nullable = false, length = 50)
    private String userName;

    @Column(nullable = false, length = 120, unique = true)
    private String email;

    @Column(nullable = false)
    private String encryptedPassword;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "organization_id" ,nullable = false)
    private OrganizationEntity organization;

    @Column(length = 30)
    private String phoneNumber;

    @Column
    private String address;

//    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JoinColumn(name = "address_id" ,nullable = false)
//    private AddressEntity addresses;

    @ManyToMany( cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinTable(name = "users_tasks", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id")
    ,inverseJoinColumns = @JoinColumn(name="task_id", referencedColumnName = "id"))
    private List<TaskEntity> tasks;


}
