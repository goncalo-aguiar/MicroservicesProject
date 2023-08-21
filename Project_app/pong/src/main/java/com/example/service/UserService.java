package com.example.service;

import com.example.repository.UserRepository;
import com.example.domain.User;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Singleton
public class UserService {

    @Inject
    private UserRepository userRepository;

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }



    public User save(User user) {
        return userRepository.save(user);
    }

    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }



    // New method to find a user by ID as an integer
    public User findUserById(long id) {
        return userRepository.findById(id).orElse(null);
    }

    // New method to update a user in the database
    @Transactional
    public void updateUser(User user) {
        userRepository.update(user);
    }
    // New method to update a user's best score in the database
    @Transactional
    public void updateUserBestScore(Long id, int bestScore) {
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            user.setBestScore(bestScore);
            userRepository.save(user);
        }
    }
}
