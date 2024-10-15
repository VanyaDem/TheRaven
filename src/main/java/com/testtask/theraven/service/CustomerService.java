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
        return repository.findAll();

    }

    public Customer getById(Long id) {
        return repository
                .findById(id)
                .orElseThrow();
    }

    public Customer add(Customer customer) {
        return repository.save(customer);
    }

    public Customer update(Long id, Customer updatedCustomer) {

        var oldCustomer = repository.findById(id).orElseThrow();

        constraintsCheck(id, oldCustomer, updatedCustomer);

        oldCustomer.setUpdated(System.currentTimeMillis());
        oldCustomer.setFullName(updatedCustomer.getFullName());
        oldCustomer.setPhone(updatedCustomer.getPhone());

        return repository.save(oldCustomer);
    }



    public void delete(Long id) {
        var customer = repository.findById(id).orElseThrow();
        customer.setIsActive(false);
        repository.flush();
    }

    private void constraintsCheck(Long id, Customer old, Customer upd){
        if(!old.getEmail().equals(upd.getEmail())){
            throw new RuntimeException("Email can not be edited!"); //todo: create specific exception
        }
        if (!id.equals(upd.getId())){
            throw  new RuntimeException("Id can not be edited!"); //todo: create specific exception
        }
    }
}
