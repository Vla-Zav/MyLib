package com.example.demo.entitys;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Data;

import java.util.UUID;

@Data
@Entity
public class Coffee {
    @Id
    private String id;
    private String name;
    public Coffee(String id, String name){
        this.id = id;
        this.name = name;
    };
    public Coffee(String name){
        this(UUID.randomUUID().toString(), name);
    }

    public Coffee() {

    }
}
