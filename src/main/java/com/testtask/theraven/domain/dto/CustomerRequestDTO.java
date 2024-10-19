package com.testtask.theraven.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

/**
 * Data Transfer Object (DTO) for customer creation or update requests.
 * This class extends {@link CustomerDTO} to provide the necessary fields
 * for customer requests while hiding the ID field.
 *
 * @author Ivan Demydenko
 */
@Data
@Builder
public class CustomerRequestDTO extends AbstractCustomerDTO {

    @JsonIgnore
    private Long id;

    @NotBlank(message = "Full name is mandatory and cannot be blank")
    @Size(min = 2, max = 50, message = "Full name must be between 2 and 50 characters")
    private String fullName;

    @Pattern(regexp = "^[^\\s@]+@[^\\s@]+\\.[^\\s@]+$", message = "Email should be valid") // I didn't use the @Email annotation because it limits email to 75 characters (the test task requirements say 100)
    @Size(min = 2, max = 100, message = "Email must be between 2 and 100 characters long")
    private String email;

    @Pattern(regexp = "^\\+\\d{5,13}$", message = "Phone number must start with '+' followed by 5 to 13 digits")
    private String phone;
}
