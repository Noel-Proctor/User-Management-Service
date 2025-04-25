package com.npro.UserManagementService.UserRepositoryTests;
import com.npro.UserManagementService.model.Users;
import com.npro.UserManagementService.repository.UsersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.zip.DataFormatException;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class UserRepositoryTest {

    @Test
    void contextLoads() {
    }

    private final UsersRepository usersRepository;
    private Users validUser1;
    private Users validUser2;
    private Users inValidUser1;


    @BeforeEach
    void setUp() {
        validUser1=new Users("validUser1", "secretPasswordHash", true);
        validUser2=new Users("validUser2", "secretPasswordHash", true);
        inValidUser1=new Users();

    }


    @Autowired
    public UserRepositoryTest(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    //Test that a valid user can be created and saved.
    @Test
    void testValidUserSavesAndReturnsResult(){

        Users user = new Users();
        user.setUsername("Test username");
        user.setPasswordHash("secretpasswordhash");
        user.setIsActive(true);

        Users savedUser = usersRepository.save(user);

        Users fetchedUser = usersRepository.findById(savedUser.getId()).get();

        assertNotNull(fetchedUser);
        assertEquals(savedUser.getUsername(), fetchedUser.getUsername(), "User name should match");
        assertEquals(savedUser.getPasswordHash(), fetchedUser.getPasswordHash(), "PasswordHash should match");
        assertEquals(savedUser.getIsActive(), fetchedUser.getIsActive(), "Is active should match");
    }

//    Test Database constraint preventing same username being submitted twice.
    @Test
    void testOnlyUniqueUsernamesCanBeSaved(){
        validUser2.setUsername(validUser1.getUsername());
        Optional<Users> savedUser = usersRepository.findByUsername(validUser1.getUsername());

        if(savedUser.isPresent()){
            assertThrows(DataIntegrityViolationException.class, ()-> usersRepository.save(validUser1));
        }else{
            usersRepository.save(validUser1);
            assertThrows(DataIntegrityViolationException.class, ()-> usersRepository.save(validUser2));
        }



    }

}

