package com.example.location.controller;

import com.example.location.model.Geodata;
import com.example.location.model.Weather;
import com.example.location.repository.GeodataRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/location")
public class LocationController {

    @Autowired
    private GeodataRepository repository;

    @Autowired
    private RestTemplate restTemplate;

    private final String weatherServiceUrl = "http://weather-info-service/weather";


    @GetMapping
    public List<Geodata> getAllLocations() {
        List<Geodata> list = new ArrayList<>();
        repository.findAll().forEach(list::add);
        return list;
    }

    @GetMapping(params = "name")
    public Optional<Geodata> getLocationByName(@RequestParam("name") String name) {
        return repository.findByName(name);
    }

    @PostMapping
    public Geodata save(@RequestBody Geodata geodata) {
        return repository.save(geodata);
    }

    @PutMapping
    public Geodata update(@RequestParam("name") String name, @RequestBody Geodata updated) {
        Geodata geodata = repository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Location not found"));
        geodata.setLatitude(updated.getLatitude());
        geodata.setLongitude(updated.getLongitude());
        geodata.setName(updated.getName());
        return repository.save(geodata);
    }

    @DeleteMapping
    public String delete(@RequestParam("name") String name) {
        Geodata geodata = repository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Location not found"));
        repository.delete(geodata);
        return "Deleted " + name;
    }

    @GetMapping("/weather")
    public Weather getWeather(@RequestParam("name") String name) {
        Geodata geodata = repository.findByName(name)
                .orElseThrow(() -> new RuntimeException("Location not found"));
        String url = String.format("%s?lat=%s&lon=%s", weatherServiceUrl,
                geodata.getLatitude(), geodata.getLongitude());
        return restTemplate.getForObject(url, Weather.class);
    }
}