package com.cobeliii.customer;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;

class CustomerDataAccessServiceTest {

    private CustomerDataAccessService underTest;
    @Mock
    private CustomerRepository customerRepository;
    private AutoCloseable autoCloseable;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new CustomerDataAccessService(customerRepository);
    }

    @AfterEach
    void tearDown() throws Exception {
        autoCloseable.close();
    }

    @Test
    void itShouldSelectAllCustomers() {
        //Act
        underTest.selectAllCustomers();
        //Assert
        Mockito.verify(customerRepository).findAll();
    }

    @Test
    void itShouldSelectCustomerById() {
        //Arrange
        int id = 1;

        //Act
        underTest.selectCustomerById(id);

        //Assert
        Mockito.verify(customerRepository).findById(id);
    }

    @Test
    void itShouldInsertCustomer() {
        //Arrange
        Customer customer = new Customer("Newton", "newton@gmail.com", 28);

        //Act
        underTest.insertCustomer(customer);

        //Assert
        Mockito.verify(customerRepository).save(customer);
    }

    @Test
    void itShouldExistsPersonWithEmail() {
        //Arrange
        Customer customer = new Customer("Newton", "newton@gmail.com", 28);

        //Act
        underTest.existsPersonWithEmail(customer.getEmail());

        //Assert
        Mockito.verify(customerRepository).existsCustomerByEmail(customer.getEmail());
    }

    @Test
    void itShouldExistsPersonWithId() {
        //Arrange
        Customer customer = new Customer("Newton", "newton@gmail.com", 28);

        //Act
        underTest.existsPersonWithId(customer.getId());

        //Assert
        Mockito.verify(customerRepository).existsCustomerById(customer.getId());
    }

    @Test
    void itShouldDeleteCustomerById() {

        //Arrange
        int id = 1;

        //Act
        underTest.deleteCustomerById(id);

        //Assert

        Mockito.verify(customerRepository).deleteById(id);
    }

    @Test
    void itShouldUpdateCustomer() {
        //Arrange
        Customer customer = new Customer(1, "Newton", "newton@gmail.com", 28);

        //Act
        underTest.updateCustomer(customer);

        //Assert
        Mockito.verify(customerRepository).save(customer);
    }
}