package com.example.liquibaseandtestcontainers.controllers;

import com.example.liquibaseandtestcontainers.entitys.User;
import com.example.liquibaseandtestcontainers.repa.UsersRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class UserController {
    private UsersRepository usersRepository;

    @GetMapping("/getUsers")
    public ResponseEntity<List<User>> getUsers(){
        List<User> users = usersRepository.findAll();
        return ResponseEntity.ok().body(users);
    }
}
