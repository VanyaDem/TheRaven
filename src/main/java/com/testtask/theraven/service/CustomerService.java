package com.testtask.theraven.service;

import com.testtask.theraven.domain.entity.Customer;

import java.util.List;
import java.util.NoSuchElementException;

/**
 * Interface for managing customer data.
 *
 * This interface provides methods for retrieving, adding, updating, and deleting customers.
 */
public interface CustomerService {

    /**
     * Retrieves a list of all customers.
     *
     * @return a list of customers
     */
    List<Customer> getAll();

    /**
     * Retrieves a customer by their unique identifier (ID).
     *
     * @param id the unique identifier of the customer
     * @return the customer if found
     * @throws NoSuchElementException if a customer with the given ID does not exist
     */
    Customer getById(Long id);

    /**
     * Adds a new customer.
     *
     * @param customer the customer to be added
     * @return the added customer
     */
    Customer add(Customer customer);

    /**
     * Updates the data of a customer with the given ID.
     *
     * @param id the unique identifier of the customer to be updated
     * @param updatedCustomer the new customer data
     * @return the updated customer
     * @throws NoSuchElementException if a customer with the given ID does not exist
     */
    Customer update(Long id, Customer updatedCustomer);

    /**
     * Deletes a customer by their unique identifier (ID).
     *
     * @param id the unique identifier of the customer to be deleted
     * @throws NoSuchElementException if a customer with the given ID does not exist
     */
    void delete(Long id);
}
