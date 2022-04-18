package com.magic.spring.serviceTest;

import com.magic.spring.DAO.User;
import com.magic.spring.mapper.UserMapper;
import com.magic.spring.service.UserService;
import com.magic.spring.service.exception.UsernameDuplicatedException;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.security.NoSuchAlgorithmException;

/**
 * @author Hsy
 * @Description @SpringBootTest will let this class won't be packaged into a jar file.
 * @RunWith will let this class run with SpringRunner.
 *
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class UserServiceTest {

    @Autowired
    private UserService userService;
    /**
     * Remember to set the environment variables again in the test configuration.
     * Like what you did for application. Because the real application and test class have different runners.
     * So they can't share the environment variables in runners.
     *
     * Test the register method. Remember to set different username.
     * Don't test it too much time. Or you will get a lots of useless users.
     * If you want to test, Please comment out @Ignore.
     */
    @Ignore
    @Test
    public void reg_successfully() throws NoSuchAlgorithmException {
        User user = new User();
        user.setUsername("test2");
        user.setPassword("123456");
        // Mark the test user as deleted.
        user.setIsDeleted(1);
        Integer rows = userService.register(user);
        // If the affected rows is 1, it means the register is successful.
        Assertions.assertEquals(1, rows);
    }

    /**
     * Try to register a user with the same username.
     * It should throw a UsernameDuplicatedException.
     */
    @Test
    public void DuplicatedUsername(){
        User user = new User();
        user.setUsername("Hsj");
        user.setPassword("123456");
        user.setIsDeleted(1);
        // If the exception is thrown, then the test passes.
        Assertions.assertThrows(UsernameDuplicatedException.class, () -> {userService.register(user);});
    }
}
