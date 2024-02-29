package com.cobeliii.customer;

import com.cobeliii.exception.ResourceNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class CustomerServiceTest {

    @Mock
    private CustomerDao customerDao;
    private CustomerService underTest;
    private AutoCloseable autoCloseable;

    @BeforeEach
    void setUp() {
        autoCloseable = MockitoAnnotations.openMocks(this);
        underTest = new CustomerService(customerDao);
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void itShouldGetAllCustomers() {
        //Act
        underTest.getAllCustomers();

        //Assert
        Mockito.verify(customerDao).selectAllCustomers();
    }

    @Test
    void itShouldGetCustomer() {
        //Arrange
        int id = 1;
        Customer customer = new Customer(id, "Alex", "alex@gmail.com",19);
        Mockito.when(customerDao.selectCustomerById(id)).thenReturn(Optional.of(customer));

        //Act

        Customer actual = underTest.getCustomer(id);

        //Assert
        Assertions.assertThat(actual).isEqualTo(customer);
    }

    @Test
    void itShouldThrowWhenGetCustomerReturnEmptyOptional() {
        //Arrange
        int id = 1;

        Mockito.when(customerDao.selectCustomerById(id)).thenReturn(Optional.empty());

        //Act
        //Assert
        Assertions.assertThatThrownBy(()-> underTest.getCustomer(id))
                .isInstanceOf(ResourceNotFoundException.class)
                .hasMessage("customer with id [%s] not found".formatted(id));
    }

    @Test
    void itShouldAddCustomer() {
        //Arrange
        String email = "alex@gmail.com";
        Mockito.when(customerDao.existsPersonWithEmail(email)).thenReturn(false);
        CustomerRegistrationRequest request = new CustomerRegistrationRequest(
                "Alex",
                email,
                19
        );
        //Act
        underTest.addCustomer(request);

        //Assert
        ArgumentCaptor<Customer> customerArgumentCaptor = ArgumentCaptor.forClass(Customer.class);
        Mockito.verify(customerDao).insertCustomer(customerArgumentCaptor.capture());
        Customer captureCustomer = customerArgumentCaptor.getValue();

        Assertions.assertThat(captureCustomer.getId()).isNull();
        Assertions.assertThat(captureCustomer.getName()).isEqualTo(request.name());
        Assertions.assertThat(captureCustomer.getEmail()).isEqualTo(request.email());
        Assertions.assertThat(captureCustomer.getAge()).isEqualTo(request.age());

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