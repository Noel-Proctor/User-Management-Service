package com.npro.UserManagementService.ModelValidationTests;

import com.npro.UserManagementService.model.Application;
import com.npro.UserManagementService.model.Role;
import com.npro.UserManagementService.model.Users;
import com.npro.UserManagementService.testUtil.BaseValidationTest;
import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.*;
import java.util.Set;
@SpringBootTest
public class ApplicationModelValidationTest extends BaseValidationTest {


    @Test
    void testValidApplicationValidation(){

        Application validApplication = new Application();
        validApplication.setApplication_name("Valid application name");
        validApplication.setOwner(
                new Users("Valid username", "secretpasswordhash", true));

        Role role = new Role();
        role.setApplication(validApplication);
        role.setRoleName("Valid role name");
        validApplication.addApplication_role(role);
        Set<ConstraintViolation<Application>> violations = validator.validate(validApplication);
        assertThat(violations).isEmpty();
        Set<Role> roles = validApplication.getApplication_roles();
        assertThat(roles).contains(role);
    }

    @Test
    void testInvalidApplicationValidation(){
        Application invalidApplication=new Application();
        invalidApplication.setApplication_name("");
        invalidApplication.setOwner(null);
        invalidApplication.addApplication_role(null);

        Set<ConstraintViolation<Application>> violations = validator.validate(invalidApplication);
        assertThat(violations).isNotEmpty();


        assertThat(violations).anyMatch(
                v-> v.getPropertyPath().toString().equals("owner") &&
                        v.getMessage().equals("must not be null")
        );

        assertThat(violations).anyMatch(v->
                v.getPropertyPath().toString().equals("application_name") &&
                        v.getMessage().equals("Application name must be between 1-50 characters long")
        );

        assertThat(violations).anyMatch(v->
                v.getPropertyPath().toString().equals("application_roles") &&
                        v.getMessage().equals("must not be null")
        );
    }




}
