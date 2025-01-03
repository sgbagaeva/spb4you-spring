package com.example.spb4you.services;

import com.example.spb4you.models.Photo;
import com.example.spb4you.repositories.PhotoRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PhotoService extends GenericService<Photo, Integer> {
    private final PhotoRepository photoRepository;

    public PhotoService(PhotoRepository photoRepository) {
        super(photoRepository);
        this.photoRepository = photoRepository;
    }

    // Метод для поиска фотографии по описанию
    public Optional<Photo> findByDescription(String description) {
        return photoRepository.findByDescription(description);
    }
}
