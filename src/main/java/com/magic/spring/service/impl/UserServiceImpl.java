package com.magic.spring.service.impl;


import com.magic.spring.DAO.User;
import com.magic.spring.mapper.UserMapper;
import com.magic.spring.service.UserService;
import com.magic.spring.service.exception.InsertException;
import com.magic.spring.service.exception.UsernameDuplicatedException;
import lombok.AllArgsConstructor;
import org.apache.tomcat.util.security.MD5Encoder;
import org.postgresql.shaded.com.ongres.scram.common.bouncycastle.pbkdf2.SHA256Digest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;
import java.util.UUID;

/**
 * @author Hsy
 */
@Service("userService")
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    /**
     * Constructor for UserServiceImpl
     * @Description Make the member parameters to be final and
     *              set a constructor instead of using the @Autowired annotation.
     *              Use the @AllArgsConstructor annotation to make the constructor.
     */

    @Override
    public Integer register(User user) throws NoSuchAlgorithmException {

        // Check if the username is duplicated
        String username = user.getUsername();
        User result = userMapper.findUserByUsername(username);
        if (result != null) {
            throw new UsernameDuplicatedException("The username is duplicated");
        }

        // Encode the password by BCrypt algorithm.
        // It's the default algorithm of Spring Security.
        // It will generate a random salt in password automatically.
        // So we don't need to save the salt in the database.
        String realPassword = user.getPassword();
        user.setPassword(passwordEncoder.encode(realPassword));

        // Complete the log information.
        user.setCreatedUser(user.getUsername());
        user.setModifiedUser(user.getUsername());
        Date data = new Date();
        user.setCreatedTime(data);
        user.setModifiedTime(data);

        // Insert the user
        Integer rows = userMapper.insertUser(user);
        if (rows != 1) {
            throw new InsertException("There is an exception when inserting the user");
        }

        return rows;
    }

//    private String encodePassword(String password, String salt) throws NoSuchAlgorithmException {
//
//        for (int i = 0; i < ENCRYPTION_TIME; i++) {
//            MessageDigest messageDigest = MessageDigest.getInstance(ENCRYPTION_METHOD);
//            messageDigest.update((salt + password + salt).getBytes());
//            BigInteger sha = new BigInteger(messageDigest.digest());
//            password = sha.toString(32).toUpperCase();
//        }
//        return password;
//
//    }
}
