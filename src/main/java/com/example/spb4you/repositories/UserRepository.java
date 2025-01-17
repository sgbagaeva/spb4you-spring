package com.example.spb4you.repositories;

import com.example.spb4you.models.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    // Дополнительные методы для User
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
}
