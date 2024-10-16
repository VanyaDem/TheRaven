package com.testtask.theraven.domain.dto;


import lombok.Data;

@Data
public abstract class AbstractCustomerDTO {

    private Long id;

    private String fullName;

    private String email;

    private String phone;
}
