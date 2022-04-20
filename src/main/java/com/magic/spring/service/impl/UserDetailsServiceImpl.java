package com.magic.spring.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.magic.spring.DAO.User;
import com.magic.spring.mapper.UserMapper;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Hsy
 * @Description Implements UserDetailsService interface. It's for Spring Security.
 */
@Service("userDetailsService")
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserMapper userMapper;

    /**
     * @param username String
     * @return User It extends UserDetails.
     * @throws UsernameNotFoundException
     * @Description Load user by username.
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        // Mybatis-Plus query wrapper
//        QueryWrapper<User> wrapper = new QueryWrapper();
        List<GrantedAuthority> auths;
        if (username != null) {
            User user = userMapper.findUserByUsername(username);
            if (Objects.isNull(user)) {
                throw new UsernameNotFoundException(String.format("User %s is not found", username));
            }
            if (user.getRole() == 1) {
                // Add admin authority
                auths = AuthorityUtils.commaSeparatedStringToAuthorityList("ROlE_ADMIN");
            } else {
                // Add regular authority
                auths = AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_REGULAR");
            }
            return new org.springframework.security.core.userdetails.User(user.getUsername(), new BCryptPasswordEncoder().encode(user.getPassword()), true, true, true, true, auths);
        }
        auths = AuthorityUtils.commaSeparatedStringToAuthorityList("ROLE_ANONYMOUS");
        return new org.springframework.security.core.userdetails.User("anon", "anno", true, true, true, true, auths);

    }
}
