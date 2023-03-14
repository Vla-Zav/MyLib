package com.example.demo.controller;

import com.example.demo.entitys.Droid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/droid")
@AllArgsConstructor
public class DroidController {
    private final Droid droid;

    @GetMapping
    Droid getDroid(){
        return droid;
    }
}
