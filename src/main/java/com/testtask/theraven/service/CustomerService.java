package com.testtask.theraven.service;

import com.testtask.theraven.domain.entity.Customer;

import java.util.List;

public interface CustomerService {

    List<Customer> getAll();

    Customer getById(Long id);

    Customer add(Customer customer);

    Customer update(Long id, Customer updatedCustomer);

    void delete(Long id);

}
