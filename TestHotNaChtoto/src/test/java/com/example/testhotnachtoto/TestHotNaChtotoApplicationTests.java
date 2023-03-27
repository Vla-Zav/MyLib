package com.example.testhotnachtoto;

import com.example.testhotnachtoto.entitys.User;
import com.example.testhotnachtoto.repa.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
class TestHotNaChtotoApplicationTests {

    @Autowired
    private UserRepository userRepository;
    @Test
    void findUserById() {
        assertEquals(2, userRepository.count());
    }
}