package com.testtask.theraven.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * Data Transfer Object (DTO) for customer creation or update requests.
 * This class extends {@link CustomerDTO} to provide the necessary fields
 * for customer requests while hiding the ID field.
 *
 * @author Ivan Demydenko
 */
public class CustomerRequestDTO extends CustomerDTO{

    @JsonIgnore
    private Long id;
}
