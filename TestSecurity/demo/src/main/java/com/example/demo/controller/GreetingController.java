package com.example.demo.controller;

import com.example.demo.entitys.Greeting;
import lombok.Data;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/greeting")
@Data
public class GreetingController {
    private final Greeting greeting;

    @GetMapping
    String getGreetingName(){
        return greeting.getName();
    }

    @GetMapping("/coffee")
    String getGreetingCoffee(){
        return greeting.getCoffee();
    }
}
