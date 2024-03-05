package com.cobeliii.customer;

import com.cobeliii.exception.DuplicateResourceException;
import com.cobeliii.exception.RequestValidationException;
import com.cobeliii.exception.ResourceNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerService {
    private final CustomerDao customerDao;

    public CustomerService(CustomerDao customerDao) {
        this.customerDao = customerDao;
    }

    public List<Customer> getAllCustomers(){
        return customerDao.selectAllCustomers();
    }

    public Customer getCustomer(Integer id){
        return customerDao.selectCustomerById(id).orElseThrow(()-> new ResourceNotFoundException("customer with id [%s] not found".formatted(id)));
    }

    public void addCustomer(CustomerRegistrationRequest request){
        //Check if email exists
        String email = request.email();
        if (customerDao.existsCustomerWithEmail(email)){
            throw new DuplicateResourceException("Email already taken");
        }

        Customer customer = new Customer(request.name(), request.email(), request.age());
        customerDao.insertCustomer(customer);
    }

    public void deleteCustomerById(Integer id) {
        if (customerDao.existsCustomerById(id)){
            throw new ResourceNotFoundException("customer with id [%s] not found".formatted(id));
        }

        customerDao.deleteCustomerById(id);
    }

    public void updateCustomer(Integer customerId, CustomerUpdateRequest updateRequest) {
        Customer customer = getCustomer(customerId);

        boolean changes = false;

        if (updateRequest.name() != null && !updateRequest.name().equals(customer.getName())){
            customer.setName(updateRequest.name());
            changes = true;
        }

        if (updateRequest.age() != null && !updateRequest.age().equals(customer.getAge())){
            customer.setAge(updateRequest.age());
            changes = true;
        }

        if (updateRequest.email() != null && !updateRequest.email().equals(customer.getEmail())){
            if (customerDao.existsCustomerWithEmail(updateRequest.email())){
                throw new DuplicateResourceException("email already taken");
            }
            customer.setEmail(updateRequest.email());
            changes = true;
        }

        if (!changes){
            throw new RequestValidationException("No data changes found.");
        }

        customerDao.updateCustomer(customer);

    }
}
