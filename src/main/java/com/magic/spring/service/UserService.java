package com.magic.spring.service;

import com.magic.spring.DAO.User;

import java.security.NoSuchAlgorithmException;

/**
 * @author Hsy
 */
public interface UserService {
    /**
     * Register a user.
     * @param user Object of User
     * @return The affected rows
     */
    Integer register(User user) throws NoSuchAlgorithmException;
}
