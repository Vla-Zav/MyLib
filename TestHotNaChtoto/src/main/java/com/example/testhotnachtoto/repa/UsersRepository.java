package com.example.testhotnachtoto.repa;

import com.example.testhotnachtoto.entitys.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<User, Long> {
}
