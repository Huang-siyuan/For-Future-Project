package com.magic.spring.mapperTest;

import com.magic.spring.DAO.User;
import com.magic.spring.mapper.UserMapper;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Hsy
 * @Description @SpringBootTest will let this class won't be packaged into a jar file.
 * @RunWith will let this class run with SpringRunner.
 *
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class UserMapperTest {

    // Interface can't create a bean. But mybatis can automatically proxy.
//    private final UserMapper userMapper;
    @Autowired
    private UserMapper userMapper;
//    /**
//     * @Author Hsy
//     * @Description We make the member parameter final and write a constructor to realize the autowired.
//     *              In this way, we don't have to use @Autowired to inject the UserMapper.
//     *              It's the newest and better way than using @Autowired.
//     */
//    public UserMapperTest(UserMapper userMapper) {
//        this.userMapper = userMapper;
//    } Damn it! It's not the newest and better way. It can be used in testing class to autowire.

    /* Remember to set the environment variables again in the test configuration. Like what you did for application. */
    @Test
    public void insertTest(){
        User user = new User();
        user.setUsername("Hsy");
        user.setPassword("123456");
        Integer rows = userMapper.insertUser(user);
        System.out.println(rows);
    }
}
