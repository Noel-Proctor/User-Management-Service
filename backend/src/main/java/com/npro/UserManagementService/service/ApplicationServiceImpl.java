package com.npro.UserManagementService.service;

import com.npro.UserManagementService.model.Application;
import com.npro.UserManagementService.model.User;
import com.npro.UserManagementService.payload.ApplicationDTO;
import com.npro.UserManagementService.repository.ApplicationRepository;
import com.npro.UserManagementService.utils.AuthorisationService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final ModelMapper modelMapper;

    public ApplicationServiceImpl(ApplicationRepository applicationRepository, ModelMapper modelMapper) {
        this.applicationRepository = applicationRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ApplicationDTO createNewApplication(User user, String applicationName, String applicationDescription) {
        AuthorisationService.authoriseCreateNewApplication(user);
        Application application = new Application();
        application.setCreatedOn(LocalDate.now());
        application.setApplication_name(applicationName);
        application.setOwner(user);
        application.setGuid(UUID.randomUUID().toString());
        application.setApplication_description(applicationDescription);
        ApplicationDTO applicationDTO = modelMapper.map(applicationRepository.save(application), ApplicationDTO.class);
        return applicationDTO;
    }

}

