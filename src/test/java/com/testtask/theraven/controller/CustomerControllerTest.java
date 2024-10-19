package com.testtask.theraven.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.testtask.theraven.domain.dto.CustomerDTO;
import com.testtask.theraven.domain.entity.Customer;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;
import java.util.List;

import static com.testtask.theraven.util.TestUtils.*;

@AutoConfigureMockMvc
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@TestPropertySource(locations = "/application-test.properties")
class CustomerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private EntityManager entityManager;


    @SneakyThrows
    @Test
    void addCustomer_should_return_201_status() {
        var dto = createCustomerDto();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectToJson(objectMapper, dto)))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @SneakyThrows
    @Test
    void addCustomer_add_new_customer_to_DB() {
        var dto = createCustomerDto();

        mockMvc.perform(MockMvcRequestBuilders.post("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectToJson(objectMapper, dto)))
                .andExpect(MockMvcResultMatchers.status().isCreated());

        var customer = entityManager.find(Customer.class, 1L);
        Assertions.assertNotNull(customer);

    }

    @SneakyThrows
    @Test
    void getAll_should_return_200_status() {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @SneakyThrows
    @Test
    @Transactional
    void getAll_return_all_customers() {
        createListOfCustomers().stream().peek(c -> c.setId(null)).forEach(entityManager::persist);

        var res = mockMvc.perform(MockMvcRequestBuilders.get("/api/customers")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
                .getContentAsString();

        List<CustomerDTO> customers = Arrays.asList(objectMapper.readValue(res, CustomerDTO[].class));

        Assertions.assertEquals(2, customers.size());

    }

    @SneakyThrows
    @Test
    @Transactional
    void getById_should_return_200_status() {

        createListOfCustomers().stream().peek(c -> c.setId(null)).forEach(entityManager::persist);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/customers/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @SneakyThrows
    @Test
    @Transactional
    void getById_return_customer() {
        createListOfCustomers().stream().peek(c -> c.setId(null)).forEach(entityManager::persist);

        var res = mockMvc.perform(MockMvcRequestBuilders.get("/api/customers/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn()
                .getResponse()
                .getContentAsString();

        CustomerDTO customer = objectMapper.readValue(res, CustomerDTO.class);

        Assertions.assertEquals(1, customer.getId());
        Assertions.assertEquals("Ivan Demydenko", customer.getFullName());
        Assertions.assertEquals("vanyaDem@gmail.com", customer.getEmail());

    }

    @SneakyThrows
    @Test
    @Transactional
    void getById_hide_no_active_customers() {
        createListOfCustomers().stream().peek(c -> c.setId(null)).forEach(entityManager::persist);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/customers/2")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @SneakyThrows
    @Test
    @Transactional
    void updateCustomer_update_customer_id() {
        createListOfCustomers().stream().peek(c -> c.setId(null)).forEach(entityManager::persist);
        CustomerDTO dto = CustomerDTO.builder()
                .id(15L)
                .fullName("Ivan Demydenko")
                .email("vanyaDem@gmail.com")
                .phone("+380556667788")
                .build();

        mockMvc.perform(MockMvcRequestBuilders.put("/api/customers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectToJson(objectMapper, dto)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        var customer = entityManager.find(Customer.class, 15L);

        Assertions.assertNotNull(customer);
        Assertions.assertEquals("vanyaDem@gmail.com", customer.getEmail());

    }

    @SneakyThrows
    @Test
    @Transactional
    void updateCustomer_update_customer() {
        createListOfCustomers().stream().peek(c -> c.setId(null)).forEach(entityManager::persist);
        CustomerDTO dto = CustomerDTO.builder()
                .id(1L)
                .fullName("Poltavskiy Paliy")
                .email("vanyaDem@gmail.com")
                .phone("+380556667799")
                .build();

        mockMvc.perform(MockMvcRequestBuilders.put("/api/customers/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectToJson(objectMapper, dto)))
                .andExpect(MockMvcResultMatchers.status().isOk());

        var customer = entityManager.find(Customer.class, 1L);

        Assertions.assertNotNull(customer);
        Assertions.assertEquals("vanyaDem@gmail.com", customer.getEmail());
        Assertions.assertEquals("Poltavskiy Paliy", customer.getFullName());
        Assertions.assertEquals("+380556667799", customer.getPhone());

    }

    @SneakyThrows
    @Test
    @Transactional
    void delete_should_return_204_status() {
        createListOfCustomers().stream().peek(c -> c.setId(null)).forEach(entityManager::persist);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/customers/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @SneakyThrows
    @Test
    @Transactional
    void delete_should_set_isActive_false() {
        createListOfCustomers().stream().peek(c -> c.setId(null)).forEach(entityManager::persist);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/customers/1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        var noActiveCustomer = entityManager.find(Customer.class, 1L);

        Assertions.assertFalse(noActiveCustomer.getIsActive());
        Assertions.assertEquals(1, noActiveCustomer.getId());
    }
}
