package com.example.h2springboot.demo.config;

import com.example.h2springboot.demo.service.CustomAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final CustomAuthenticationProvider customAuthenticationProvider;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public SecurityConfig(CustomAuthenticationProvider customAuthenticationProvider, BCryptPasswordEncoder passwordEncoder) {
        this.customAuthenticationProvider = customAuthenticationProvider;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(this.customAuthenticationProvider).passwordEncoder(this.passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
       http.csrf().disable().antMatcher("/**").authorizeRequests()
               .antMatchers("/h2**","/login**").permitAll()
               .anyRequest().authenticated()
               .and()
               .formLogin().loginPage("/login")
               .defaultSuccessUrl("/home")
               .and()
               .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).permitAll()
               .and()
               .sessionManagement().maximumSessions(1)
               .expiredUrl("/login");
    }
}
