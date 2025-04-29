package com.npro.UserManagementService.UserRepositoryTests;
import com.npro.UserManagementService.model.System_Role;
import com.npro.UserManagementService.model.User;
import com.npro.UserManagementService.repository.UsersRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
public class UserRepositoryTest {

    @Test
    void contextLoads() {
    }

    private final UsersRepository usersRepository;
    private User validUser1;
    private User validUser2;



    @BeforeEach
    void setUp() {
        validUser1=new User("validUser1", "secretPasswordHash", true);
        validUser2=new User("validUser2", "secretPasswordHash", true);
        validUser1.setSystemRole(System_Role.BASIC_USER);
        validUser2.setSystemRole(System_Role.BASIC_USER);


    }


    @Autowired
    public UserRepositoryTest(UsersRepository usersRepository) {
        this.usersRepository = usersRepository;
    }

    //Test that a valid user can be created and saved.
    @Test
    void testValidUserSavesAndReturnsResult(){

        User user = new User();
        user.setUsername("Test username");
        user.setPasswordHash("secretpasswordhash");
        user.setIsActive(true);
        user.setSystemRole(System_Role.BASIC_USER);

        User savedUser = usersRepository.save(user);

        User fetchedUser = usersRepository.findById(savedUser.getId()).get();

        assertNotNull(fetchedUser);
        assertEquals(savedUser.getUsername(), fetchedUser.getUsername(), "User name should match");
        assertEquals(savedUser.getPasswordHash(), fetchedUser.getPasswordHash(), "PasswordHash should match");
        assertEquals(savedUser.getIsActive(), fetchedUser.getIsActive(), "Is active should match");
    }

//    Test Database constraint preventing same username being submitted twice.
    @Test
    void testOnlyUniqueUsernamesCanBeSaved(){
        validUser2.setUsername(validUser1.getUsername());
        Optional<User> savedUser = usersRepository.findByUsername(validUser1.getUsername());

        if(savedUser.isPresent()){
            assertThrows(DataIntegrityViolationException.class, ()-> usersRepository.save(validUser1));
        }else{
            usersRepository.save(validUser1);
            assertThrows(DataIntegrityViolationException.class, ()-> usersRepository.save(validUser2));
        }



    }

}

