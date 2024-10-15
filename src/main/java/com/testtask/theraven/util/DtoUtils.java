package com.testtask.theraven.util;

import com.testtask.theraven.domain.dto.CustomerDTO;
import com.testtask.theraven.domain.entity.Customer;

import java.util.List;

/**
 * Utility class for converting between Customer and CustomerDTO objects.
 * This class provides static methods to transform lists of customers
 * to lists of customer data transfer objects (DTOs) and vice versa.
 *
 * @author Ivan Demydenko
 */
public class DtoUtils {

    /**
     * Converts a list of Customer entities to a list of CustomerDTOs.
     *
     * @param customers the list of customers to convert
     * @return a list of CustomerDTOs
     */
    public static List<CustomerDTO> toList(List<Customer> customers) {
        return customers
                .stream()
                .map(DtoUtils::toDto)
                .toList();
    }

    /**
     * Converts a Customer entity to a CustomerDTO.
     *
     * @param customer the customer entity to convert
     * @return a CustomerDTO representation of the given customer
     */
    public static CustomerDTO toDto(Customer customer) {
        var dto = new CustomerDTO();
        dto.setId(customer.getId());
        dto.setFullName(customer.getFullName());
        dto.setEmail(customer.getEmail());
        dto.setPhone(customer.getPhone());
        return dto;
    }

    /**
     * Converts a CustomerDTO to a Customer entity.
     * This method sets the created and updated timestamps
     * to the current system time and marks the customer as active.
     *
     * @param dto the CustomerDTO to convert
     * @return a Customer entity representation of the given DTO
     */
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
