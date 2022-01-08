package by.htp.ramanouski.taskmanager.service.impl;

import by.htp.ramanouski.taskmanager.dto.OrganizationDto;
import by.htp.ramanouski.taskmanager.entity.AddressEntity;
import by.htp.ramanouski.taskmanager.entity.OrganizationEntity;
import by.htp.ramanouski.taskmanager.repository.OrganizationRepository;
import by.htp.ramanouski.taskmanager.service.OrganizationService;
import by.htp.ramanouski.taskmanager.service.ServiceUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class OrganizationServiceImpl implements OrganizationService {

    private final OrganizationRepository organizationRepository;
    private final ServiceUtils serviceUtils;

    private final static ModelMapper MAPPER = new ModelMapper();

    @Autowired
    public OrganizationServiceImpl(OrganizationRepository organizationRepository,
                                   ServiceUtils serviceUtils) {
        this.organizationRepository = organizationRepository;
        this.serviceUtils = serviceUtils;
    }

    @Override
    public OrganizationEntity findByOrganizationName(String organizationName) {
        OrganizationEntity organization = organizationRepository.findByOrganizationName(organizationName);
        if(organization == null){
            return null;
        }
        return organization;
    }

    @Override
    public OrganizationDto findByOrganizationId(String organizationId) {
        OrganizationEntity organization = organizationRepository.findByOrganizationId(organizationId);
        if(organization == null){
            throw new RuntimeException("There is no organization with id " + organizationId);
        }
        OrganizationDto returnedValue = MAPPER.map(organization,OrganizationDto.class);
        return returnedValue;
    }

    @Override
    public OrganizationDto createNewOrganization(OrganizationDto organizationDto) {

        if (findByOrganizationName(organizationDto.getOrganizationName()) != null ){
            throw new RuntimeException("Organization with such name already exists");
        }
        OrganizationEntity organizationEntity = MAPPER.map(organizationDto,OrganizationEntity.class);

        organizationEntity.getAddress().setAddressId(serviceUtils.generatePublicAddressId());
        organizationEntity.getAddress().setAddressOrganization(organizationEntity);

        organizationEntity.setOrganizationId(serviceUtils.generatePublicOrganizationId());
        organizationEntity.setUsers(new ArrayList<>());

        OrganizationEntity savedOrganization = organizationRepository.save(organizationEntity);
        OrganizationDto returnedValue = MAPPER.map(savedOrganization,OrganizationDto.class);

        return returnedValue;
    }



}
