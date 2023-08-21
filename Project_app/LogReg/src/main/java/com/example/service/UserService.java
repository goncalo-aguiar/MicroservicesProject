package com.example.service;

import com.example.domain.Role;
import com.example.domain.User;
import com.example.repository.RoleRepository;
import com.example.repository.UserRepository;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;


import javax.validation.ConstraintViolationException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Singleton
public class UserService {

    @Inject
    private UserRepository userRepository;

    @Inject
    RoleRepository roleRepository;

    public User save(User user) {
        return userRepository.save(user);
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public List<User> findAll() {
        return (List<User>) userRepository.findAll();
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    public Optional<User> findByUsername(String username) { return userRepository.findByUsername(username); }

    public User register(User user) {
        Optional<User> existingUser = userRepository.findByUsername(user.getUsername());
        if (existingUser.isPresent()) {
            throw new ConstraintViolationException("Username already exists", new HashSet<>());
        }

        Role userRole = roleRepository.findOrCreateByName("USER");
        user.setRoles(Set.of(userRole));
        return userRepository.save(user);
    }
}
