package com.example.spb4you.repositories;
import com.example.spb4you.models.Tag;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TagRepository extends CrudRepository<Tag, Integer> {
    // Дополнительные методы для Tag, если нужно
    Optional<Tag> findByName(String name);
}

