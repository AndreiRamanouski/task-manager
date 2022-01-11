package by.htp.ramanouski.taskmanager.repository;

import by.htp.ramanouski.taskmanager.entity.OrganizationEntity;
import by.htp.ramanouski.taskmanager.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface OrganizationRepository extends CrudRepository<OrganizationEntity, Long> {
    OrganizationEntity findByOrganizationName(String testOrganization);

    OrganizationEntity findByOrganizationId(String organizationId);
}
