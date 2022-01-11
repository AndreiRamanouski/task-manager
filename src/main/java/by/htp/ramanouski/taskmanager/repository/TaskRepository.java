package by.htp.ramanouski.taskmanager.repository;

import by.htp.ramanouski.taskmanager.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, Long> {

    TaskEntity findTaskByTaskId(String taskId);
}
