package com.example.location.controller;

import com.example.location.model.Location;
import com.example.location.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/location")
public class LocationController {

    @Autowired
    private LocationRepository repository;

    @GetMapping
    public Iterable<Location> findAll() {
        return repository.findAll();
    }

    @GetMapping("/{cityName}")
    public ResponseEntity<Location> findByCity(@PathVariable String cityName) {
        Location loc = repository.findByCityName(cityName);
        return loc != null ? ResponseEntity.ok(loc) : ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Location> save(@RequestBody Location location) {
        return new ResponseEntity<>(repository.save(location), HttpStatus.CREATED);
    }
}
