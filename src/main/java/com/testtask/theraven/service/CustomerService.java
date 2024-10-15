package com.testtask.theraven.service;

import com.testtask.theraven.domain.entity.Customer;
import com.testtask.theraven.persistence.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository repository;

    public List<Customer> getAll() {
        return repository.findAll()
                .stream()
                .filter(Customer::getIsActive)
                .toList();
    }

    public Customer getById(Long id) {
        return repository
                .findById(id)
                .filter(Customer::getIsActive)
                .orElseThrow();
    }

    public Customer add(Customer customer) {
        return repository.save(customer);
    }

    public Customer update(Long id, Customer updatedCustomer) {

        var oldCustomer = repository.findById(id).orElseThrow();

        emailCheck(oldCustomer.getEmail(), updatedCustomer.getEmail());

        oldCustomer.setUpdated(System.currentTimeMillis());
        oldCustomer.setFullName(updatedCustomer.getFullName());
        oldCustomer.setPhone(updatedCustomer.getPhone());

        return repository.save(oldCustomer);
    }


    public void delete(Long id) {
        var customer = repository.findById(id).orElseThrow();
        customer.setIsActive(false);
        repository.save(customer);
    }

    private void emailCheck(String email, String dtoEmail){
        if(!email.equals(dtoEmail)){
            throw new RuntimeException("Email can not be edited!"); //todo: create specific exception
        }
    }
}
