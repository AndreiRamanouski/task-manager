package by.htp.ramanouski.taskmanager.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Collection;
import java.util.List;

@Data
@Builder
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

    @Column(length = 50)
    private String lastName;

    @Column(nullable = false, length = 120, unique = true)
    private String email;

    @Column(nullable = false)
    private String encryptedPassword;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "organization_id", nullable = false)
    private OrganizationEntity organization;

    @ManyToMany(cascade = CascadeType.PERSIST)
    @JoinTable(name = "users_tasks", joinColumns = @JoinColumn(name = "user_id", referencedColumnName = "id")
            , inverseJoinColumns = @JoinColumn(name = "task_id", referencedColumnName = "id"))
    private List<TaskEntity> tasks;

    @ManyToMany(cascade = {CascadeType.PERSIST},
            fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles" ,
            joinColumns = @JoinColumn(name = "users_id",referencedColumnName = "id")
            ,inverseJoinColumns = @JoinColumn(name = "roles_id",referencedColumnName = "id"))
    private Collection<RoleEntity> roles;


}
