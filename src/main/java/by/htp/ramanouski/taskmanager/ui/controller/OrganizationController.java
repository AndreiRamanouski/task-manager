package by.htp.ramanouski.taskmanager.ui.controller;

import by.htp.ramanouski.taskmanager.dto.OrganizationDto;
import by.htp.ramanouski.taskmanager.service.OrganizationService;
import by.htp.ramanouski.taskmanager.ui.model.response.organization.OrganizationRestResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/organizations")  // http:///localhost:8080/users
public class OrganizationController {

    private final OrganizationService organizationService;

    @Autowired
    public OrganizationController(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    @GetMapping("/{organizationId}")
    public OrganizationRestResponse getOrganization(@PathVariable String organizationId){
        OrganizationDto organizationDto = organizationService.findByOrganizationId(organizationId);

        if(organizationDto == null){
            throw new RuntimeException("No organization with such id" + organizationId);
        }

        ModelMapper mapper = new ModelMapper();
        OrganizationRestResponse returnedValue = mapper.map(organizationDto, OrganizationRestResponse.class);

        return returnedValue;
    }


}
