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

}
