package com.example.liquibaseandtestcontainers.repa;

import com.example.liquibaseandtestcontainers.entitys.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<User, Long> {
}
