package com.testtask.theraven.domain.dto;


import com.testtask.theraven.domain.entity.Customer;
import lombok.Data;

/**
 * Data Transfer Object (DTO) for the {@link Customer} entity.
 * This class is used to transfer customer-related data between the presentation and service layers
 * without exposing the full Customer entity.
 *
 * @author Ivan Demydenko
 */
@Data
public class CustomerDTO {

    private Long id;

    private String fullName;

    private String email;

    private String phone;

    /**
     * Converts a {@link Customer} entity to a {@link CustomerDTO}.
     *
     * @param customer The {@link Customer} entity to be converted.
     * @return A new instance of {@link CustomerDTO} with the data copied from the given entity.
     */
    public static CustomerDTO of(Customer customer) { // by information expert pattern
        var dto = new CustomerDTO();
        dto.setId(customer.getId());
        dto.setFullName(customer.getFullName());
        dto.setEmail(customer.getEmail());
        dto.setPhone(customer.getPhone());
        return dto;
    }

}
