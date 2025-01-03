package com.example.spb4you.services;

import com.example.spb4you.models.Point;
import com.example.spb4you.repositories.PointRepository;
import com.example.spb4you.repositories.RouteRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PointService extends GenericService<Point, Integer> {
    private final PointRepository pointRepository;

    public PointService(PointRepository pointRepository) {
        super(pointRepository);
        this.pointRepository = pointRepository;
    }

}

