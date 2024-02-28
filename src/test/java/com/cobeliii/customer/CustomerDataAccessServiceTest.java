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
        //Act
        //Assert
    }

    @Test
    void itShouldExistsPersonWithEmail() {
        //Arrange
        //Act
        //Assert
    }

    @Test
    void itShouldExistsPersonWithId() {
        //Arrange
        //Act
        //Assert
    }

    @Test
    void itShouldDeleteCustomerById() {
        //Arrange
        //Act
        //Assert
    }

    @Test
    void itShouldUpdateCustomer() {
        //Arrange
        //Act
        //Assert
    }
}