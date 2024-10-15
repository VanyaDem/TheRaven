package com.testtask.theraven.service;

import com.testtask.theraven.domain.entity.Customer;
import com.testtask.theraven.exception.EmailEditException;
import com.testtask.theraven.persistence.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.function.Supplier;

/**
 * Service class for managing customer-related operations.
 * This class provides methods for retrieving, adding, updating, and deleting customers.
 * It ensures that business logic related to customer management is encapsulated.
 *
 * @author Ivan Demydenko
 */
@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository repository;

    /**
     * Retrieves a list of all active customers.
     *
     * @return a list of active customers
     */
    public List<Customer> getAll() {
        return repository.findAll()
                .stream()
                .filter(Customer::getIsActive)
                .toList();
    }

    /**
     * Retrieves a customer by their identifier.
     *
     * @param id the identifier of the customer
     * @return the customer with the specified identifier
     * @throws NoSuchElementException if no customer is found with the given id or if the customer is inactive
     */
    public Customer getById(Long id) {
        return repository
                .findById(id)
                .filter(Customer::getIsActive)
                .orElseThrow(createNotFoundException(id));
    }

    /**
     * Adds a new customer to the repository.
     *
     * @param customer the customer to add
     * @return the added customer
     */
    public Customer add(Customer customer) {
        return repository.save(customer);
    }

    /**
     * Updates the information of an existing customer.
     *
     * @param id              the identifier of the customer to update
     * @param updatedCustomer the updated customer information
     * @return the updated customer
     * @throws NoSuchElementException if no customer is found with the given id
     * @throws RuntimeException       if an attempt is made to change the customer's email
     */
    public Customer update(Long id, Customer updatedCustomer) {
        var oldCustomer = repository
                .findById(id)
                .orElseThrow(createNotFoundException(id));

        emailCheck(oldCustomer.getEmail(), updatedCustomer.getEmail());

        oldCustomer.setUpdated(System.currentTimeMillis());
        oldCustomer.setFullName(updatedCustomer.getFullName());
        oldCustomer.setPhone(updatedCustomer.getPhone());

        return repository.save(oldCustomer);
    }

    /**
     * Deletes a customer by their identifier by marking them as inactive.
     *
     * @param id the identifier of the customer to delete
     * @throws NoSuchElementException if no customer is found with the given id
     */
    public void delete(Long id) {
        var customer = repository
                .findById(id)
                .orElseThrow(createNotFoundException(id));

        customer.setIsActive(false);
        repository.save(customer);
    }

    /**
     * Checks if the email of the customer has been modified.
     *
     * @param email    the current email of the customer
     * @param dtoEmail the new email to compare with
     * @throws EmailEditException if the email is being modified
     */
    private void emailCheck(String email, String dtoEmail) {
        if (!email.equals(dtoEmail)) {
            throw new EmailEditException("Email cannot be edited!");
        }
    }

    /**
     * Creates a {@link NoSuchElementException} supplier with a detailed message indicating that
     * a customer with the specified ID does not exist.
     *
     * This method is used to lazily throw a {@link NoSuchElementException} when a customer is not found
     * in the repository.
     *
     * @param id the identifier of the customer that was not found
     * @return a {@link Supplier} that provides a {@link NoSuchElementException} with a message
     */
    private Supplier<NoSuchElementException> createNotFoundException(Long id){
        return () -> new NoSuchElementException(String.format("Customer with id: %s no exist!", id));
    }
}

