package com.testtask.theraven.domain.dto;


import com.testtask.theraven.domain.entity.Customer;
import lombok.Data;

@Data
public class CustomerDTO {

    private Long id;

    private String fullName;

    private String email;

    private String phone;

    public static CustomerDTO of(Customer customer){
        var dto = new CustomerDTO();
        dto.setId(customer.getId());
        dto.setFullName(customer.getFullName());
        dto.setEmail(customer.getEmail());
        dto.setPhone(customer.getPhone());
        return dto;
    }

}
