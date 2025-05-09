package com.npro.UserManagementService.ServiceLayerTests;

import com.npro.UserManagementService.model.System_Role;
import com.npro.UserManagementService.model.User;
import com.npro.UserManagementService.payload.UserDTO;
import com.npro.UserManagementService.payload.UserResponse;
import com.npro.UserManagementService.repository.UserRepository;
import com.npro.UserManagementService.service.UserService;
import com.npro.UserManagementService.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UserServiceTest {
    private static UserService userService;
    private static UserRepository userRepository;
    private static ModelMapper modelMapper;

    @BeforeAll
    static void setUp() {
        userRepository = mock(UserRepository.class);
        modelMapper = mock(ModelMapper.class);
        userService = new UserServiceImpl(userRepository, modelMapper);
    }



    @Test
    public void shouldCreateNewUser(){

         User user = new User("validUsername", "validPasswordHash", true);
         user.setSystemRole(System_Role.USER_MANAGER);
         user.setEmail("test@gmail.com");

         UserDTO userRequest = new UserDTO(user.getUsername(), user.getEmail(), user.getPasswordHash());
         userRequest.setSystem_role(user.getSystem_role());

         when(userRepository.save(any(User.class))).thenAnswer( invocation ->
                 invocation.getArgument(0));

         when(modelMapper.map(userRequest, User.class)).thenReturn(user);
         when(modelMapper.map(user, UserDTO.class)).thenReturn(userRequest);

        UserResponse response = userService.createNewUser(userRequest);
        assertThat(response).isNotNull();
        assertThat(response.getUser().getUsername()).isEqualTo(user.getUsername());
        assertThat(response.getUser().getEmail()).isEqualTo(user.getEmail());
        assertThat(response.getMessage()).isEqualTo("User created successfully");
        assertThat(response.getUser().getPasswordHash()).isEqualTo(user.getPasswordHash());


    }


//
//
//    @Test
//    public void shouldCreateNewAdminUser(){
//
//    }
}
