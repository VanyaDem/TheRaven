package com.testtask.theraven.util;

import com.testtask.theraven.domain.dto.CustomerDTO;
import com.testtask.theraven.domain.entity.Customer;

import java.util.List;

public class DtoUtils {

    public static List<CustomerDTO> toList(List<Customer> customers) {
        return customers
                .stream()
                .map(DtoUtils::toDto)
                .toList();
    }

    public static CustomerDTO toDto(Customer customer) {
        var dto = new CustomerDTO();
        dto.setId(customer.getId());
        dto.setFullName(customer.getFullName());
        dto.setEmail(customer.getEmail());
        dto.setPhone(customer.getPhone());
        return dto;
    }

    public static Customer toCustomer(CustomerDTO dto) {
        var customer = new Customer();
        Long currentTime = System.currentTimeMillis();

        customer.setId(dto.getId());
        customer.setCreated(currentTime);
        customer.setUpdated(currentTime);

        customer.setFullName(dto.getFullName());
        customer.setEmail(dto.getEmail());
        customer.setPhone(dto.getPhone());

        customer.setIsActive(true);

        return customer;
    }
}
