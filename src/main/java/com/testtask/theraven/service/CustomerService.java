package com.testtask.theraven.service;

import com.testtask.theraven.domain.dto.CustomerDTO;
import com.testtask.theraven.domain.entity.Customer;
import com.testtask.theraven.persistence.repository.CustomerRepository;
import com.testtask.theraven.util.DtoUtils;
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

    public Customer update(Long id, CustomerDTO dto) {
        var oldCustomer = repository.findById(id)
                .orElseThrow();

        var updatedCustomer = DtoUtils.toCustomer(dto);

        if(!oldCustomer.getEmail().equals(updatedCustomer.getEmail())){
            throw new RuntimeException("Email can not be edited!"); //todo: create specific exception
        }

        updatedCustomer.setCreated(oldCustomer.getCreated());
        updatedCustomer.setUpdated(System.currentTimeMillis());

        repository.delete(oldCustomer);
        return repository.save(updatedCustomer);
    }

    public void delete(Long id) {
        var customer = repository.findById(id).orElseThrow();
        customer.setIsActive(false);
    }
}
