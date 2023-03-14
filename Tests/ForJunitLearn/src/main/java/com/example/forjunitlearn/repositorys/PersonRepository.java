package com.example.forjunitlearn.repositorys;

import com.example.forjunitlearn.entitys.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {

}
