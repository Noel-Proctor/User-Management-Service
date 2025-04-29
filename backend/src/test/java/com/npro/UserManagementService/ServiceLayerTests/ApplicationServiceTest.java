package com.npro.UserManagementService.ServiceLayerTests;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.*;
import com.npro.UserManagementService.exceptions.AccessDeniedRuntimeException;
import com.npro.UserManagementService.model.Application;
import com.npro.UserManagementService.model.System_Role;
import com.npro.UserManagementService.model.User;
import com.npro.UserManagementService.payload.ApplicationDTO;
import com.npro.UserManagementService.repository.ApplicationRepository;
import com.npro.UserManagementService.service.ApplicationService;
import com.npro.UserManagementService.service.ApplicationServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

public class ApplicationServiceTest {

    private ApplicationService applicationService;

    private ApplicationRepository applicationRepository;
    private ModelMapper modelMapper;

    @BeforeEach
    void setUp() {
        applicationRepository = mock(ApplicationRepository.class);
        modelMapper = mock(ModelMapper.class);
        applicationService = new ApplicationServiceImpl(applicationRepository, modelMapper);
    }


    @Test
    void shouldCreateNewApplication(){
        User user = new User("validUsername", "validPasswordHash", true);
        user.setSystemRole(System_Role.USER_MANAGER);

        when(applicationRepository.save(any())).thenAnswer(invocation -> {
            return invocation.getArgument(0); // Return the application passed to save()
        });

        when(modelMapper.map(any(), eq(ApplicationDTO.class))).thenAnswer(
                (invocation -> {
                    Application application = invocation.getArgument(0);
                    ApplicationDTO dto = new ApplicationDTO();
                    dto.setGuid(application.getGuid());
                    dto.setApplicationName(application.getApplication_name());
                    dto.setApplicationDescription(application.getApplication_description());
                    dto.setCreatedOn(application.getCreatedOn());
                    return dto;
                }));


        ApplicationDTO applicationDTO = applicationService.createNewApplication(
                user, "Test Application Name", "Test Application Description");

        assertThat(applicationDTO).isNotNull();
        assertThat(applicationDTO.getApplicationName()).isEqualTo("Test Application Name");
        assertThat(applicationDTO.getGuid().trim()).isNotEqualTo("");
    }

    @Test
    void shouldNotAllowNonManagersToCreateApplications(){
        User user = new User("validUsername", "validPasswordHash", true);
        user.setSystemRole(System_Role.BASIC_USER);

        assertThatThrownBy(() -> applicationService.createNewApplication(user,
                "Test Application Name", "Test Application Description"))
                .isInstanceOf(AccessDeniedRuntimeException.class);
    }
}
