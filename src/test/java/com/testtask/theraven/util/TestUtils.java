package com.testtask.theraven.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.testtask.theraven.domain.dto.CustomerDTO;
import com.testtask.theraven.domain.entity.Customer;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TestUtils {

    public static CustomerDTO createCustomerDto() {
        return CustomerDTO.builder()
                .id(1L)
                .fullName("Ivan Demydenko")
                .email("vanyaDem@gmail.com")
                .phone("+380556667788")
                .build();
    }

    public static List<Customer> createListOfCustomers() {

        Long currentTime = System.currentTimeMillis();

        Customer customer1 = Customer.builder()
                .id(1L)
                .created(currentTime)
                .updated(currentTime)
                .fullName("Ivan Demydenko")
                .email("vanyaDem@gmail.com")
                .phone("+380556667788")
                .isActive(true)
                .build();

        Customer customer2 = Customer.builder()
                .id(2L)
                .created(currentTime)
                .updated(currentTime)
                .fullName("Stepan Giga")
                .email("zolotoCarpat@gmail.com")
                .phone("+380556667788")
                .isActive(false)
                .build();

        Customer customer3 = Customer.builder()
                .id(3L)
                .created(currentTime)
                .updated(currentTime)
                .fullName("Petro Rat")
                .email("skilkiSebePamiataYou@gmail.com")
                .phone(null)
                .isActive(true)
                .build();

        return List.of(customer1, customer2, customer3);
    }

    public static String objectToJson(ObjectMapper objectMapper, Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String generateStringWithLength(int length) {
        return Stream
                .generate(() -> "a")
                .limit(length)
                .collect(Collectors.joining());
    }
}
