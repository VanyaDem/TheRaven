package com.testtask.theraven.presentation.controllers;

import com.testtask.theraven.domain.dto.CustomerDTO;
import com.testtask.theraven.domain.dto.CustomerRequestDTO;
import com.testtask.theraven.service.CustomerService;
import com.testtask.theraven.util.DtoUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("/api/customers")
public class CustomerController {


    private final CustomerService service;

    //todo: add understandable http statuses for each endpoint

    @PostMapping
    public ResponseEntity<CustomerDTO> addCustomer(@RequestBody CustomerRequestDTO customerRequestDTO){
        var customer = DtoUtils.toCustomer(customerRequestDTO);
        customer = service.add(customer);
        return ResponseEntity.ok(DtoUtils.toDto(customer));
    }

    @GetMapping
    public ResponseEntity<List<CustomerDTO>> getAll(){
        var customers = service.getAll();
        return ResponseEntity.ok(DtoUtils.toList(customers));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getById(@PathVariable Long id){
        var customer = service.getById(id);
        return ResponseEntity.ok(DtoUtils.toDto(customer));
    }



    @PutMapping("/{id}")
    public ResponseEntity<CustomerDTO> updateCustomer(@PathVariable Long id, @RequestBody CustomerDTO customerDTO){
        var customer = service.update(id , DtoUtils.toCustomer(customerDTO));
        return ResponseEntity.ok(DtoUtils.toDto(customer));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable Long id){
        service.delete(id);
        return ResponseEntity.ok("Customer was deleted.");
    }


}
