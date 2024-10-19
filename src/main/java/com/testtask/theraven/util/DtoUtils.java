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
        return CustomerDTO
                .builder()
                .id(customer.getId())
                .fullName(customer.getFullName())
                .email(customer.getEmail())
                .phone(customer.getPhone())
                .build();
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
        Long currentTime = System.currentTimeMillis();
        return Customer
                .builder()
                .id(dto.getId())
                .created(currentTime)
                .updated(currentTime)
                .fullName(dto.getFullName())
                .email(dto.getEmail())
                .phone(dto.getPhone())
                .isActive(true)
                .build();
    }
}
