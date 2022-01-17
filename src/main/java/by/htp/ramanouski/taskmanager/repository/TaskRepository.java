package by.htp.ramanouski.taskmanager.repository;

import by.htp.ramanouski.taskmanager.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Long> {

    TaskEntity findTaskByTaskId(String taskId);

    @Query(value = "select tasks.id, tasks.task_id, title, target_date, is_finished from tasks " +
            "join users_tasks ut on tasks.id = ut.task_id " +
            "join users u on u.id = ut.user_id " +
            "join organizations o on o.id = u.organization_id " +
            "where o.organization_id = :organizationId " +
            "group by tasks.id", nativeQuery = true)
    List<TaskEntity> findAllWhereOrganizationId(@Param("organizationId")String organizationId);
}
