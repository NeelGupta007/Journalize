package com.lazycoder.journalize.services;


import com.lazycoder.journalize.Entities.User;
import com.lazycoder.journalize.Repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.bson.types.ObjectId;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Component
@Slf4j
public class UserService {

    @Autowired
    private UserRepository userRepository;

    private static  final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void saveEntry(@NotNull User newUser) {
        try{
            userRepository.save(newUser);
        } catch (Exception e) {
            log.error("Exception : ", e);
        }
    }

    public void saveNewUser(@NotNull User newUser) {
        try{
            newUser.setPassword(passwordEncoder.encode(newUser.getPassword()));
            newUser.setRoles(Arrays.asList("USER"));
            userRepository.save(newUser);
        } catch (Exception e) {
            log.error("Exception : ", e);
        }
    }


    public List<User> getAll() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(ObjectId id) {
        return userRepository.findById(id);
    }

    public void deleteById(ObjectId id) {
        userRepository.deleteById(id);
    }

    public void deleteByUsername(String username) {
        userRepository.deleteByUsername(username);
    }

    public User findByUserName(String username) {
        return userRepository.findByUsername(username);
    }
}
