package by.htp.ramanouski.taskmanager.service.impl;

import by.htp.ramanouski.taskmanager.dto.OrganizationDto;
import by.htp.ramanouski.taskmanager.entity.OrganizationEntity;
import by.htp.ramanouski.taskmanager.repository.OrganizationRepository;
import by.htp.ramanouski.taskmanager.service.OrganizationService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrganizationServiceImpl implements OrganizationService {

    private final OrganizationRepository organizationRepository;

    @Autowired
    public OrganizationServiceImpl(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

    @Override
    public OrganizationEntity findByOrganizationName(String organizationName) {
        OrganizationEntity organization = findByOrganizationName(organizationName);
        if(organization == null){
            return null;
        }
        return organization;
    }

    @Override
    public OrganizationDto findByOrganizationId(String organizationId) {
        OrganizationEntity organization = organizationRepository.findByOrganizationId(organizationId);
        if(organization == null){
            return null;
        }
        ModelMapper mapper = new ModelMapper();
        OrganizationDto returnedValue = mapper.map(organization,OrganizationDto.class);
        return returnedValue;
    }

    @Override
    public OrganizationEntity save(OrganizationEntity organization) {
        return organizationRepository.save(organization);
    }


}
