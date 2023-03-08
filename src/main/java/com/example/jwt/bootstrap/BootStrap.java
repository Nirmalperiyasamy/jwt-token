package com.example.jwt.bootstrap;

import com.example.jwt.dao.UserDao;
import com.example.jwt.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class BootStrap {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostConstruct
    private void postConstructor() {
        if (!userAlreadyExist("Nirmal")) {
            UserDao dao = new UserDao();
            dao.setUsername("Nirmal");
            dao.setPassword(passwordEncoder.encode("1234"));
            userRepository.save(dao);
        }
    }

    private boolean userAlreadyExist(String username) {
        return userRepository.existsByUsername(username);
    }
}
