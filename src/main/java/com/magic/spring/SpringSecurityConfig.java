package com.magic.spring;

import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author Hsy
 * @Descripton Spring Security configuration.
 */
@AllArgsConstructor
@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;

    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // The HTML for login page.
                .formLogin().loginPage("/login.html")
                // The URL for login processing. Spring Security will handle it.
                .loginProcessingUrl("/user/login")
                // If we log in successfully, then jump to this URL.
                .defaultSuccessUrl("/user/index").permitAll().and()
                .authorizeRequests().and()
                .httpBasic().and()
                // CSRF is not needed for this application.
                .csrf().disable()
                .authorizeRequests()
                // Allow anonymous access to log in and register pages.
                .antMatchers("/user/reg", "/user/login", "/").permitAll()
                .anyRequest().authenticated();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
