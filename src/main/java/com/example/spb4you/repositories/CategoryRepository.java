package com.example.spb4you.repositories;
import com.example.spb4you.models.Category;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends CrudRepository<Category, Integer> {
        Optional<Category> findByName(String name);
}

