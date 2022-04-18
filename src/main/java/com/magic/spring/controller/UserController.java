package com.magic.spring.controller;

import com.magic.spring.DAO.User;
import com.magic.spring.service.UserService;
import com.magic.spring.service.exception.InsertException;
import com.magic.spring.service.exception.UsernameDuplicatedException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;

/**
 * @author Hsy
 */
@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;

    @RequestMapping("/login")
    public String login() {
        return "login";
    }

    @PostMapping("/reg")
    public ResponseEntity<?> register(@RequestBody User user) throws NoSuchAlgorithmException {
        userService.register(user);
        return ResponseEntity.ok("Have registered successfully");
    }
}
