package com.example.liquibaseandtestcontainers;

import com.example.liquibaseandtestcontainers.entitys.User;
import com.example.liquibaseandtestcontainers.repa.UsersRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.util.TestPropertyValues;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.TestPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@ContextConfiguration(initializers = LiquibaseAndTestContainersApplicationTests.Initializer.class)
@TestPropertySource(properties = {"spring.config.location=classpath:application.yml"})
class LiquibaseAndTestContainersApplicationTests {

	@Container
	public static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:15.1")
			.withDatabaseName("LiquibaseDB")
			.withUsername("Vlad")
			.withPassword("root")
			.withReuse(true);

	static class Initializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {
		@DynamicPropertySource
		public void initialize(ConfigurableApplicationContext configurableApplicationContext){
			postgreSQLContainer.start();
			TestPropertyValues.of("CONTAINER.URL=" + postgreSQLContainer.getJdbcUrl(),
							"CONTAINER.USERNAME=" + postgreSQLContainer.getUsername(),
							"CONTAINER.PASSWORD=" + postgreSQLContainer.getPassword(),
							"CONTAINER.LIQUIBASE=!prod")
					.applyTo(configurableApplicationContext.getEnvironment());
		}
	}

	@Autowired
	private UsersRepository usersRepository;
	@Test
	void findUserById() {
		var user = usersRepository.findById(1L);
		Assertions.assertThat(user.get().getUsername()).isEqualTo("admin");
	}
}
