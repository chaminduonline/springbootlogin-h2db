package com.example.h2springboot.demo;

import com.example.h2springboot.demo.dao.RoleRepository;
import com.example.h2springboot.demo.dao.UserRepository;
import com.example.h2springboot.demo.model.Role;
import com.example.h2springboot.demo.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;

@SpringBootApplication
public class Springbootloginh2dbApplication implements CommandLineRunner {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public Springbootloginh2dbApplication(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public static void main(String[] args) {
        SpringApplication.run(Springbootloginh2dbApplication.class, args);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void run(String... args) throws Exception {
        User user = new User();
        user.setUsername("root");
        user.setPassword(this.passwordEncoder.encode("123"));
        user.setRoles(Arrays.asList(new Role("USER")));
        this.userRepository.save(user);
    }
}
