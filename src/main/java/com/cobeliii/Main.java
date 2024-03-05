package com.cobeliii;

import com.cobeliii.customer.Customer;
import com.cobeliii.customer.CustomerRepository;
import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.List;

@SpringBootApplication
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    CommandLineRunner runner(CustomerRepository customerRepository){
        return args -> {
         for (int i = 0; i < 21; i++){
             Faker faker = new Faker();
             String name = faker.name().fullName();
             String email = faker.internet().safeEmailAddress();
             int age = faker.number().numberBetween(18, 55);
             Customer customer = new Customer(name, email, age);

             customerRepository.save(customer);
         }
        };
    }
}
