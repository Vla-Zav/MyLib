package com.example.liquibaseandtestcontainers;

import com.example.liquibaseandtestcontainers.controllers.UserController;
import com.example.liquibaseandtestcontainers.entitys.User;
import com.example.liquibaseandtestcontainers.repa.UsersRepository;
import jakarta.transaction.Transactional;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.List;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
class LiquibaseAndTestContainersApplicationTests {

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private UserController userController;

    @Test
    @Transactional
    void testUserCreate(){
        User newUser = new User();
        newUser.setUsername("Vlad");
        newUser.setPassword("pass");
        newUser.setEmail("test@mail.ru");
        Assertions.assertThat(userController.createUser(newUser).getStatusCode().value()).isEqualTo(201);
        Assertions.assertThat(usersRepository.count()).isEqualTo(3);
    }
    @Test
    void findUsersTest() {
        List<User> users = userController.getUsers().getBody();
        Assertions.assertThat(users).isNotNull();
        Assertions.assertThat(users.size()).isEqualTo(2);
    }

    @Test
    void testGetUserById(){
        User user = userController.getUserById(1L).getBody();
        Assertions.assertThat(user.getUsername()).isEqualTo("admin");
    }

    @Test
    @Transactional
    void testPutUser(){
        User user = new User();
        user.setUsername("newAdm");
        user.setEmail("newadm@mail.ru");
        user.setPassword("newPass");
        Assertions.assertThat(userController.putUser(1L, user).getStatusCode().value()).isEqualTo(200);
        User editUser = usersRepository.findById(1L).get();
        Assertions.assertThat(editUser.getUsername()).isEqualTo("newAdm");
    }

    @Test
    @Transactional
    void testDeleteUser(){
        userController.deleteUser(1L);
        Assertions.assertThat(usersRepository.count()).isEqualTo(1);
    }
}
