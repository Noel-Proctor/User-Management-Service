package com.npro.UserManagementService.ModelValidationTests;
import com.npro.UserManagementService.model.System_Role;
import com.npro.UserManagementService.model.User;
import com.npro.UserManagementService.testUtil.BaseValidationTest;
import jakarta.validation.ConstraintViolation;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;


import java.util.Set;

@DataJpaTest
public class UserModelValidationTest extends BaseValidationTest {


    private final String emptyString="";
//    private String fifty1chars = "aqaqaqaqaqaqaqaqaqaqaqaqaqaqaqaqaqaqaqaqaqaqaqaqaqa";

//    Assert that valid data does not throw a violation exception
    @Test
    void testCreateValidUser(){
        User user = new User();
        user.setUsername("Valid username");
        user.setPasswordHash("secretpasswordhash");
        user.setIsActive(true);
        user.setSystemRole(System_Role.BASIC_USER);
        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertTrue(violations.isEmpty());
    }
//Test violations thrown for invalid data.
    @Test
    void testInvalidUser(){
        User user = new User();
        user.setUsername(emptyString);
        user.setPasswordHash(emptyString);
        user.setSystemRole(null);

        Set<ConstraintViolation<User>> violations = validator.validate(user);

        assertThat(violations).anyMatch(v ->
                v.getPropertyPath().toString().equals("username") &&
                        v.getMessage().equals("Username must be between 3-50 characters")
        );

        assertThat(violations).anyMatch(v ->
                v.getPropertyPath().toString().equals("username") &&
                        v.getMessage().equals("must not be blank")
        );

        assertThat(violations).anyMatch(v->
                v.getPropertyPath().toString().equals("passwordHash") &&
                v.getMessage().equals("must not be blank"));

        assertThat(violations).anyMatch(v->
                v.getPropertyPath().toString().equals("passwordHash") &&
                        v.getMessage().equals("size must be between 10 and 50"));

        assertThat(violations).anyMatch(v->
                v.getPropertyPath().toString().equals("system_role") &&
                        v.getMessage().equals("must not be null"));

    }
}
