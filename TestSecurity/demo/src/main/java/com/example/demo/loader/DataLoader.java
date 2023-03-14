package com.example.demo.loader;

import com.example.demo.entitys.Coffee;
import com.example.demo.repository.CoffeeRepository;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@AllArgsConstructor
public class DataLoader {
    private final CoffeeRepository coffeeRepository;

    @PostConstruct
    private void loadData(){
        coffeeRepository.saveAll(List.of(
                new Coffee("Latte"),
                new Coffee("Expresso mne pohui"),
                new Coffee("Default")
                ));
    }
}
