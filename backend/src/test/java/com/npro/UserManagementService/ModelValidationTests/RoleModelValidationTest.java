package com.npro.UserManagementService.ModelValidationTests;

import com.npro.UserManagementService.model.Application;
import com.npro.UserManagementService.model.Role;
import com.npro.UserManagementService.model.User;
import com.npro.UserManagementService.testUtil.BaseValidationTest;
import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Set;
import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
public class RoleModelValidationTest extends BaseValidationTest {

    private Application validApplication;

    @BeforeEach
     void setUp(){
        validApplication=new Application();
        validApplication.setApplication_name("Valid application name");
        validApplication.setOwner(
                new User("Valid username", "secretpasswordhash", true));

    }

    @Test
    void testValidRole(){
        Role validRole = new Role();
        validRole.setRoleName("Valid role name");
        validRole.setApplication(validApplication);
        Set<ConstraintViolation<Role>> violations = validator.validate(validRole);
        assertThat(violations).isEmpty();
    }




    @Test
    void testInvalidRole(){
        Role invalidRole = new Role();
        invalidRole.setRoleName("");
        invalidRole.setApplication(null);
        Set<ConstraintViolation<Role>> violations = validator.validate(invalidRole);
        assertThat(violations).isNotEmpty();


        assertThat(violations).anyMatch(v->
                v.getPropertyPath().toString().equals("application") &&
                        v.getMessage().equals("must not be null")
        );

        assertThat(violations).anyMatch(v->
                v.getPropertyPath().toString().equals("roleName") &&
                v.getMessage().equals("Role Name must be be between 1-50 characters."));

    }
}
