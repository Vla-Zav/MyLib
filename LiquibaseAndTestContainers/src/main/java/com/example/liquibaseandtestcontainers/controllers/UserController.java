package com.example.liquibaseandtestcontainers.controllers;

import com.example.liquibaseandtestcontainers.entitys.User;
import com.example.liquibaseandtestcontainers.repa.UsersRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {
    private UsersRepository usersRepository;

    @GetMapping
    public ResponseEntity<List<User>> getUsers(){
        List<User> users = usersRepository.findAll();
        return ResponseEntity.ok().body(users);
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id){
        User user = usersRepository.findById(id).get();
        return ResponseEntity.ok().body(user);
    }

    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody @Valid User newUser){
        usersRepository.save(newUser);
        return ResponseEntity.status(201).body(newUser);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> putUser(@PathVariable Long id, @RequestBody @Valid User editUser) throws EntityNotFoundException {
        if(usersRepository.findById(id).isPresent()) {
            editUser.setId(id);
            usersRepository.save(editUser);
            return ResponseEntity.ok().body(editUser);
        }
        else return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<User> deleteUser(@PathVariable Long id){
        if(usersRepository.findById(id).isPresent()) {
            usersRepository.deleteById(id);
            return ResponseEntity.ok().build();
        }
        else return ResponseEntity.notFound().build();
    }
}
