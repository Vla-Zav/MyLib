package com.example.testhotnachtoto;

import com.example.testhotnachtoto.repa.UsersRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers

class TestHotNaChtotoApplicationTests {

    @Autowired
    private UsersRepository usersRepository;
    @Test
    void findUserById() {
        var user = usersRepository.findById(1L);
        Assertions.assertThat(user.get().getName()).isEqualTo("admin");
    }
}