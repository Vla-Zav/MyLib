package com.example.forjunitlearn;



import com.example.forjunitlearn.entitys.Person;
import com.example.forjunitlearn.repositorys.PersonRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class ForJunitLearnApplicationTests {
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private MockMvc mockMvc;
    @AfterEach
    public void resetDB(){
        personRepository.deleteAll();
    }

    private Person createTestPerson(String name){
        Person person = new Person(name);
        return personRepository.save(person);
    }
    @Test
    void createPerson() throws Exception {
        Person person = new Person();
        person.setName("Vladik");
        mockMvc.perform(post("/persons").content(objectMapper.writeValueAsString(person))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is(201));
    }

    @Test
    void getPersonById() throws Exception{
        createTestPerson("Vlad");
        Long id = createTestPerson("Sasha").getId();
        createTestPerson("Andrey");
        mockMvc.perform(get("/persons/{id}", id))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(id))
                .andExpect(jsonPath("$.name").value("Sasha"));
    }

    @Test
    void putPerson() throws Exception{
        Long id = createTestPerson("Vlad").getId();
        mockMvc.perform(put("/persons/{id}", id)
                .content(objectMapper.writeValueAsString(new Person("Andrey")))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    void personDelete() throws Exception{
        createTestPerson("Vlad");
        Long id = 1L;
        mockMvc.perform(delete("/persons/{id}", id))
                .andExpect(status().isOk());
    }

    @Test
    void personGetNotFound() throws Exception{
        mockMvc.perform(get("/persons/8"))
                .andExpect(status().isNotFound());
    }

    @Test
    void personDeleteNotFound() throws Exception{
        mockMvc.perform(delete("/persons/5"))
                .andExpect(status().isNotFound());
    }

    @Test
    void personPutNotFound() throws Exception{
        Long id = 3L;
        mockMvc.perform(put("/persons/{id}", id)
                        .content(objectMapper.writeValueAsString(new Person("Vlad")))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
