package com.example.spb4you.services;
import com.example.spb4you.models.Category;
import com.example.spb4you.repositories.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryService extends GenericService<Category, Integer> {
    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        super(categoryRepository);
        this.categoryRepository = categoryRepository;
    }

    // Метод для поиска категории по названию
    public Optional<Category> findByName(String name) {
        return categoryRepository.findByName(name);
    }
}
