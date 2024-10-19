package com.testtask.theraven.domain.dto;

import com.testtask.theraven.domain.entity.Customer;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

/**
 * Data Transfer Object (DTO) for the {@link Customer} entity.
 * This class is used to transfer customer-related data between the presentation and service layers
 * without exposing the full Customer entity.
 *
 * @author Ivan Demydenko
 */

@Data
@Builder
public class CustomerDTO {

    private Long id;

    @NotBlank(message = "Full name is mandatory and cannot be blank")
    @Size(min = 2, max = 50, message = "Full name must be between 2 and 50 characters")
    private String fullName;

    @NotBlank(message = "Email is mandatory and cannot be blank")
    @Pattern(regexp = "^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$", message = "Email should be valid")
    @Size(min = 2, max = 100, message = "Email must be between 2 and 100 characters long")
    private String email;

    @Pattern(regexp = "^\\+\\d{5,12}$", message = "Phone number must start with '+' followed by 5 to 13 digits")
    private String phone;

}

