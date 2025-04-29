package com.npro.UserManagementService.ControllerTests;
import com.npro.UserManagementService.controllers.ApplicationController;

import com.npro.UserManagementService.model.User;
import com.npro.UserManagementService.payload.ApplicationDTO;
import com.npro.UserManagementService.payload.NewApplicationRequest;
import com.npro.UserManagementService.service.ApplicationService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.UUID;
import com.fasterxml.jackson.databind.ObjectMapper;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(ApplicationController.class)
public class ApplicationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ApplicationService applicationService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser(username = "testuser", roles = {"USER_MANAGER"})
    void shouldCreatNewApplicationAndReturn201(){

        NewApplicationRequest newApplicationRequest =
                new NewApplicationRequest("Test Application Name", "Test Application Description");

        ApplicationDTO mockApp = new ApplicationDTO();
        mockApp.setApplicationName("Test Application Name");
        mockApp.setApplicationDescription("Test Application Description");
        mockApp.setCreatedOn(LocalDate.now());
        mockApp.setGuid(UUID.randomUUID().toString());

        Mockito.when(applicationService.createNewApplication(any(User.class), any(String.class), any(String.class)))
                .thenReturn(mockApp);

        try {
            mockMvc.perform(post("/api/v1/applications").with(csrf())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(newApplicationRequest)))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.message").value("Application created successfully"))
                    .andExpect(jsonPath("$.application.applicationName").value("Test Application Name"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}