package com.example.forjunitlearn;



import com.example.forjunitlearn.entitys.Person;
import com.example.forjunitlearn.repositorys.PersonRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@ContextConfiguration(initializers = ForJunitLearnApplicationTests.Initializer.class)
@TestPropertySource(properties = {"spring.config.location=classpath:application-properties.yml"})
class ForJunitLearnApplicationTests {

    @Container
    public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:15.1")
            .withDatabaseName("MyTestContainer")
            .withReuse(true);

    static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext>{
        public void initialize(ConfigurableApplicationContext configurableApplicationContext){
            TestPropertyValues.of("CONTAINER.URL=" + postgreSQLContainer.getJdbcUrl(),
                    "CONTAINER.USERNAME=" + postgreSQLContainer.getUsername(),
                    "CONTAINER.PASSWORD=" + postgreSQLContainer.getPassword())
                    .applyTo(configurableApplicationContext.getEnvironment());
        }
    }

    @Test
    void testContainer(){
    }
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private PersonRepository personRepository;
    @Autowired
    private MockMvc mockMvc;

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
