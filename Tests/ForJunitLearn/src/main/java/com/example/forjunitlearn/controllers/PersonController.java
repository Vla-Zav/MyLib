package com.example.forjunitlearn.controllers;

import com.example.forjunitlearn.entitys.Person;
import com.example.forjunitlearn.repositorys.PersonRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/persons")
@AllArgsConstructor
public class PersonController {
    private PersonRepository personRepository;

    @GetMapping
    public ResponseEntity<List<Person>> listAllPersons() {
        List<Person> persons = personRepository.findAll();
        return ResponseEntity.ok().body(persons);
    }

    @GetMapping("/{personId}")
    public ResponseEntity<Person> getPerson(@PathVariable Long personId)
            throws EntityNotFoundException {

        Optional<Person> person = personRepository.findById(personId);
        if (!person.isPresent())
            throw new EntityNotFoundException("id-" + personId);
        return ResponseEntity.ok().body(person.get());
    }

    @PostMapping
    public ResponseEntity<Person> createPerson(@RequestBody @Valid Person person) {
        Person p = personRepository.save(person);
        return ResponseEntity.status(201).body(p);
    }

    @PutMapping("/{personId}")
    public ResponseEntity<Person> updatePerson(@RequestBody @Valid Person person,
                                               @PathVariable Long personId) throws EntityNotFoundException {
        Optional<Person> p = personRepository.findById(personId);
        if (!p.isPresent())
            throw new EntityNotFoundException("id-" + personId);
        return ResponseEntity.ok().body(personRepository.save(person));
    }

    @DeleteMapping("/{personId}")
    public ResponseEntity<Person> deletePerson(@PathVariable Long personId)
            throws EntityNotFoundException {
        Optional<Person> person = personRepository.findById(personId);
        if (!person.isPresent())
            throw new EntityNotFoundException("id-" + personId);
        personRepository.deleteById(personId);
        return ResponseEntity.ok().body(person.get());
    }
}
