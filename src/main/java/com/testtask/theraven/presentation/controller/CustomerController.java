package com.testtask.theraven.presentation.controller;

import com.testtask.theraven.domain.dto.CustomerDTO;
import com.testtask.theraven.domain.dto.CustomerRequestDTO;
import com.testtask.theraven.service.CustomerService;
import com.testtask.theraven.util.DtoUtils;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for managing customers.
 *
 * @author Ivan Demydenko
 */
@RestController
@RequiredArgsConstructor
@Validated
@CrossOrigin("*")
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService service;

    /**
     * Adds a new customer.
     *
     * @param customerRequestDTO the object containing the new customer's data
     * @return ResponseEntity with the information of the created customer
     */
    @PostMapping
    public ResponseEntity<CustomerDTO> addCustomer(@RequestBody @Valid CustomerRequestDTO customerRequestDTO) {
        var customer = DtoUtils.toCustomer(customerRequestDTO);
        customer = service.add(customer);
        return ResponseEntity.status(HttpStatus.CREATED).body(DtoUtils.toDto(customer));
    }

    /**
     * Retrieves a list of all customers.
     *
     * @return ResponseEntity with a list of customers
     */
    @GetMapping
    public ResponseEntity<List<CustomerDTO>> getAll() {
        var customers = service.getAll();
        return ResponseEntity.ok(DtoUtils.toList(customers));
    }

    /**
     * Retrieves a customer by their identifier.
     *
     * @param id the identifier of the customer
     * @return ResponseEntity with the customer's information
     */
    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getById(@PathVariable Long id) {
        var customer = service.getById(id);
        return ResponseEntity.ok(DtoUtils.toDto(customer));
    }

    /**
     * Updates the information of an existing customer.
     *
     * @param id          the identifier of the customer to update
     * @param customerDTO the object with the new customer data
     * @return ResponseEntity with the updated customer's information
     */
    @PutMapping("/{id}")
    public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable Long id, @RequestBody @Valid CustomerRequestDTO customerDTO) {
        var customer = service.update(id, DtoUtils.toCustomer(customerDTO));
        return ResponseEntity.ok(DtoUtils.toDto(customer));
    }

    /**
     * Deletes a customer by their identifier.
     *
     * @param id the identifier of the customer to delete
     * @return ResponseEntity with status 204 No Content
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}

