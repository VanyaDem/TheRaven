package com.testtask.theraven.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
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
    @ValueSource(strings = {"", "vanya", "@mail.com", "vanya@@gmail.com", "@", "the longer email"})
    void addCustomer_throw_exception_if_email_is_not_valid(String email) {
        emailValidation(() -> MockMvcRequestBuilders.post("/api/customers"), email);
    }

    @SneakyThrows
    @ParameterizedTest
    @ValueSource(strings = {"", "vanya", "@mail.com", "vanya@@gmail.com", "@", "the longer email"})
    void updateCustomer_throw_exception_if_email_is_not_valid(String email) {
        emailValidation(() -> MockMvcRequestBuilders.put("/api/customers/1"), email);
    }

    @SneakyThrows
    private void emailValidation(Supplier<MockHttpServletRequestBuilder> requestBuilderSupplier, String emailFromValueSource) {
        var dto = createCustomerDto();
        dto.setEmail(setValueOrGenerated(emailFromValueSource));

        mockMvc.perform(requestBuilderSupplier.get()
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectToJson(objectMapper, dto)))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    private String setValueOrGenerated(String emailFromValueSource) {
        return (emailFromValueSource.equals("the longer email")) ? generateTheLongerEmail() : emailFromValueSource;
    }
}
