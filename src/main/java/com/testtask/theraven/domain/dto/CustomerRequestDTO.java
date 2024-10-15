package com.testtask.theraven.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class CustomerRequestDTO extends CustomerDTO{

    @JsonIgnore
    private Long id;
}
