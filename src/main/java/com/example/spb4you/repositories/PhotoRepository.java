package com.example.spb4you.repositories;

import com.example.spb4you.models.Photo;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PhotoRepository extends CrudRepository<Photo, Integer> {
    // Дополнительные методы для Photo, если нужно
    Optional<Photo> findByDescription(String description);
}
