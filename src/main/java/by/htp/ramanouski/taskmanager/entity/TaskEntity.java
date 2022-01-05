package by.htp.ramanouski.taskmanager.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tasks")
public class TaskEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Column
    private String taskId;

    @Column
    private String title;

    @Column
    private LocalDate targetDate;

    @Column
    private boolean isFinished;

    @ManyToMany(mappedBy = "tasks" ,cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    private List<UserEntity> users;
}
