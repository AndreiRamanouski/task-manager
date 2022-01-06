package by.htp.ramanouski.taskmanager.service;

import by.htp.ramanouski.taskmanager.dto.OrganizationDto;
import by.htp.ramanouski.taskmanager.entity.OrganizationEntity;

public interface OrganizationService {
    OrganizationEntity findByOrganizationName(String organizationName);

    OrganizationDto findByOrganizationId(String organizationId);

    OrganizationEntity save(OrganizationEntity organization);
}
