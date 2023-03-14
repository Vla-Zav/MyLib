package com.example.forjunitlearn.controllers;

import com.example.forjunitlearn.entitys.Person;
import com.example.forjunitlearn.repositorys.PersonRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/persons")
@AllArgsConstructor
public class PersonController {
    private PersonRepository personRepository;
    @GetMapping
    public List<Person> listAllPersons(){
        return personRepository.findAll();
    }

    @GetMapping("/{personId}")
    public Person getPersonById(@PathVariable Long personId){
        return personRepository.findById(personId).orElseThrow(EntityNotFoundException::new);
    }

    @PostMapping("/create")
    public ResponseEntity<Person> createPerson(@RequestBody @Valid Person person){
        Person createdPerson = personRepository.save(person);
        return ResponseEntity.status(201).body(createdPerson);
    }

    @PutMapping("/{personId}")
    public Person updatePerson(@RequestBody @Valid Person person , @PathVariable Long personId){
        Person oldPerson = personRepository.findById(personId).orElseThrow(EntityNotFoundException::new);
        oldPerson.setName(person.getName());
        return personRepository.save(oldPerson);
    }

    @DeleteMapping("/{personId}")
    public ResponseEntity<Person> deletePerson(@PathVariable Long personId){
        Person delPerson = personRepository.findById(personId).orElseThrow(EntityNotFoundException::new);
        personRepository.deleteById(personId);
        return ResponseEntity.ok().body(delPerson);
    }
}
