package com.example.spb4you.services;

import com.example.spb4you.models.User;
import com.example.spb4you.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserService extends GenericService<User, Integer> {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        super(userRepository);
        this.userRepository = userRepository;
    }

    // Метод для поиска пользователя по имени
    public Optional<User> findByFirstName(String firstName) {
        return userRepository.findByFirstName(firstName);
    }

    // Метод для поиска пользователя по email
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}

