package com.example.spb4you.repositories;
import com.example.spb4you.models.Point;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PointRepository extends CrudRepository<Point, Integer> {
    // дополнительные методы
}

