package com.cobeliii.customer;

import com.cobeliii.AbstractTestContainers;
import com.github.javafaker.Faker;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class CustomerRepositoryTest extends AbstractTestContainers {

    @Autowired
    private CustomerRepository underTest;
    @Autowired
    private ApplicationContext applicationContext;

    @BeforeEach
    void setUp() {
        System.out.println(applicationContext.getBeanDefinitionCount());
    }

    @Test
    void itShouldCheckIfExistsCustomerByEmail() {
        //Arrange
        Faker faker = new Faker();
        String email = faker.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        String name = faker.name().fullName();

        Customer customer = new Customer(name,
                email,
                20);

        //Act
        underTest.save(customer);
        var actual = underTest.existsCustomerByEmail(email);

        //Assert
        Assertions.assertThat(actual).isTrue();

    }

    @Test
    void itShouldCheckIfExistsCustomerByEmailWhenEmailNotPresent() {
        //Arrange
        Faker faker = new Faker();
        String email = faker.internet().safeEmailAddress() + "-" + UUID.randomUUID();

        //Act
        var actual = underTest.existsCustomerByEmail(email);

        //Assert

        Assertions.assertThat(actual).isFalse();
    }

    @Test
    void itShouldCheckIfExistsCustomerById() {
        //Arrange
        Faker faker = new Faker();
        String email = faker.internet().safeEmailAddress() + "-" + UUID.randomUUID();
        String name = faker.name().fullName();

        Customer customer = new Customer(name,
                email,
                20);

        underTest.save(customer);

        //Act
        int id = underTest.findAll()
                .stream().filter(c -> c.getEmail().equals(email))
                .map(Customer::getId).findFirst()
                .orElseThrow();

        var actual = underTest.existsCustomerById(id);

        //Assert
        Assertions.assertThat(actual).isTrue();
    }

    @Test
    void itShouldCheckIfExistsCustomerByIdWhenIdNotPresent() {
        //Arrange
        int id = -1;

        //Act
        var actual = underTest.existsCustomerById(id);

        //Assert
        Assertions.assertThat(actual).isFalse();
    }
}