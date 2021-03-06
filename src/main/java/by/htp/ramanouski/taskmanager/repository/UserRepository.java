package by.htp.ramanouski.taskmanager.repository;

import by.htp.ramanouski.taskmanager.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<UserEntity, Long> {
    UserEntity findUserByEmail(String email);
    UserEntity save(UserEntity userEntity);
    UserEntity findUserByUserId(String userId);
}
