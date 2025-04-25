package com.npro.UserManagementService.testUtil;


import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;

public abstract class BaseValidationTest {


    protected static Validator validator;

    @BeforeAll
    public static void validatorSetup(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }
}
