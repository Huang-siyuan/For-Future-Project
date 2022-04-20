package com.magic.spring.controller;

import com.magic.spring.DAO.User;
import com.magic.spring.jwt.JwtTokenUtil;
import com.magic.spring.service.UserService;
import com.magic.spring.service.exception.InsertException;
import com.magic.spring.service.exception.ServiceException;
import com.magic.spring.service.exception.UsernameDuplicatedException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import java.security.NoSuchAlgorithmException;

/**
 * @author Hsy
 */
@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserDetailsService userDetailsService;

    private void authenticate(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    @RequestMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user) throws Exception {
        authenticate(user.getUsername(), user.getPassword());

        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(user.getUsername());

        final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(token);
    }

    /**
     * @param user User
     * @return ResponseEntity
     * @throws NoSuchAlgorithmException Usually it won't be thrown
     */
    @Transactional(rollbackOn = ServiceException.class)
    @PostMapping("/reg")
    @Secured({"ROLE_ANONYMOUS"})
    public ResponseEntity<?> register(@RequestBody User user) throws NoSuchAlgorithmException {
        userService.register(user);
        final UserDetails userDetails = userDetailsService
                .loadUserByUsername(user.getUsername());

        final String token = jwtTokenUtil.generateToken(userDetails);

        return ResponseEntity.ok(token);
    }

    @GetMapping("/index")
    public String index() {
        return "index";
    }
}
