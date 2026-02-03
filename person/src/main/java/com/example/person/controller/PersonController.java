package com.example.person.controller;

import com.example.person.model.Person;
import com.example.person.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@RestController
@RequestMapping("/person")
public class PersonController {

    @Autowired
    private PersonRepository repository;

    private RestTemplate restTemplate = new RestTemplate(); // Добавляем

    @GetMapping
    public Iterable<Person> findAll() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Person findById(@PathVariable int id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Person not found"));
    }

    @GetMapping(params = "name")
    public Person findByName(@RequestParam("name") String name) {
        for (Person p : repository.findAll()) {  // перебираем Iterable
            if (p.getName().equalsIgnoreCase(name)) {
                return p;
            }
        }
        throw new RuntimeException("Person not found");
    }


    @PostMapping
    public ResponseEntity<Person> save(@RequestBody Person person) {
        if (repository.findById(person.getId()).isPresent()) {
            return ResponseEntity.badRequest().build();
        } else {
            Person savedPerson = repository.save(person);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedPerson);
        }
    }
}
