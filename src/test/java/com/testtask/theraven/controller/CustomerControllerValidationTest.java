package com.testtask.theraven.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.testtask.theraven.domain.dto.CustomerDTO;
import lombok.SneakyThrows;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.function.Supplier;

import static com.testtask.theraven.util.TestUtils.*;

@AutoConfigureMockMvc
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@TestPropertySource(locations = "/application-test.properties")
public class CustomerControllerValidationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @SneakyThrows
    @ParameterizedTest
    @ValueSource(strings = {"", "    ", "V", "null", "the longer name"})
    void addCustomer_throws_exception_if_fullName_is_not_valid(String name) {
        fullNameValidation(() -> MockMvcRequestBuilders.post("/api/customers"), name);
    }

    @SneakyThrows
    @ParameterizedTest
    @ValueSource(strings = {"", "vanya", "@mail.com", "vanya@@gmail.com", "@", "null", "the longer email"})
    void addCustomer_throws_exception_if_email_is_not_valid(String email) {
        emailValidation(() -> MockMvcRequestBuilders.post("/api/customers"), email);
    }

    @SneakyThrows
    @ParameterizedTest
    @ValueSource(strings = {"", "0991112233", "+3809", "+3809911122333", "+3809911122RT"})
    void addCustomer_throws_exception_if_phone_is_not_valid(String phone) {
        phoneValidation(() -> MockMvcRequestBuilders.post("/api/customers"), phone);
    }

    @SneakyThrows
    @ParameterizedTest
    @ValueSource(strings = {"", "    ", "V", "null", "the longer name"})
    void updateCustomer_throws_exception_if_fullName_is_not_valid(String name) {
        fullNameValidation(() -> MockMvcRequestBuilders.put("/api/customers/1"), name);
    }

    @SneakyThrows
    @ParameterizedTest
    @ValueSource(strings = {"", "vanya", "@mail.com", "vanya@@gmail.com", "@", "null", "the longer email"})
    void updateCustomer_throws_exception_if_email_is_not_valid(String email) {
        emailValidation(() -> MockMvcRequestBuilders.put("/api/customers/1"), email);
    }

    @SneakyThrows
    @ParameterizedTest
    @ValueSource(strings = {"", "0991112233", "+3809", "+3809911122333", "+3809911122RT"})
    void updateCustomer_throws_exception_if_phone_is_not_valid(String phone) {
        phoneValidation(() -> MockMvcRequestBuilders.put("/api/customers/1"), phone);
    }

    @SneakyThrows
    private void fullNameValidation(Supplier<MockHttpServletRequestBuilder> requestBuilderSupplier, String nameFromValueSource) {
        var dto = createCustomerDto();
        dto.setFullName(setValueOrGenerated(nameFromValueSource));
        doRequest(requestBuilderSupplier, dto);
    }

    @SneakyThrows
    private void emailValidation(Supplier<MockHttpServletRequestBuilder> requestBuilderSupplier, String emailFromValueSource) {
        var dto = createCustomerDto();
        dto.setEmail(setValueOrGenerated(emailFromValueSource));
        doRequest(requestBuilderSupplier, dto);
    }

    @SneakyThrows
    private void phoneValidation(Supplier<MockHttpServletRequestBuilder> requestBuilderSupplier, String phoneFromValueSource) {
        var dto = createCustomerDto();
        dto.setPhone(phoneFromValueSource);
        doRequest(requestBuilderSupplier, dto);
    }

    private void doRequest(Supplier<MockHttpServletRequestBuilder> requestBuilderSupplier, CustomerDTO dto) throws Exception {
        mockMvc.perform(requestBuilderSupplier.get()
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectToJson(objectMapper, dto)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    private static String setValueOrGenerated(String valueFromValueSource) {
        return switch (valueFromValueSource) {
            case "the longer name" -> generateStringWithLength(51);
            case "the longer email" -> generateStringWithLength(100) + "@gmail.com";
            case "null" -> null;
            default -> valueFromValueSource;
        };
    }
}
