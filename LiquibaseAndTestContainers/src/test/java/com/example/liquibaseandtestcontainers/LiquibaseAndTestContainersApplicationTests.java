package com.example.liquibaseandtestcontainers;

import com.example.liquibaseandtestcontainers.controllers.UserController;
import com.example.liquibaseandtestcontainers.entitys.User;
import com.example.liquibaseandtestcontainers.repa.UsersRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

@SpringBootTest
@Testcontainers
class LiquibaseAndTestContainersApplicationTests {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private UserController userController;

    @Test
    void findUserById() {
        var user = usersRepository.findById(1L);
        Assertions.assertThat(user.get().getUsername()).isEqualTo("admin");
    }

    @Test
    void findUser() {
        List<User> users = userController.getUsers().getBody();
        Assertions.assertThat(users).isNotNull();
        Assertions.assertThat(users.size()).isEqualTo(2);
    }
}
