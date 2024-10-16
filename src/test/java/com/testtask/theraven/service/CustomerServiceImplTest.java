package com.testtask.theraven.service;

import com.testtask.theraven.domain.entity.Customer;
import com.testtask.theraven.exception.EmailEditException;
import com.testtask.theraven.repository.CustomerRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class CustomerServiceImplTest {

    @InjectMocks
    private CustomerServiceImpl service;

    @Mock
    private CustomerRepository repository;


    private List<Customer> customers;

    @BeforeEach
    public void setup() {
        customers = createListOfCustomers();
    }


    @Test
    void getAll_should_hide_no_active_customers() {
        when(repository.findAll()).thenReturn(customers);

        var foundCustomers = service.getAll();

        Assertions.assertEquals(2, foundCustomers.size());

    }

    @Test
    void getById_should_return_customer_with_provided_id() {
        when(repository.findById(1L)).thenReturn(Optional.of(customers.get(0)));

        var customer = service.getById(1L);

        Assertions.assertEquals(1, customer.getId());
    }

    @Test
    void getById_should_hide_no_active_customer() {
        when(repository.findById(2L)).thenReturn(Optional.of(customers.get(1)));

        String message = "Customer with id: 2 no exist!";

        Assertions.assertThrows(
                NoSuchElementException.class, () -> service.getById(2L), message);
    }

    @Test
    void getBy_id_should_throw_exception_if_customer_no_exist() {
        when(repository.findById(4L)).thenReturn(Optional.empty());

        String message = "Customer with id: 4 no exist!";

        Assertions.assertThrows(
                NoSuchElementException.class, () -> service.getById(4L), message);
    }


    @Test
    void add_should_call_save_method() {
        Customer newCustomer = customers.get(0);

        service.add(newCustomer);

        verify(repository, times(1)).save(newCustomer);


    }

    @Test
    void update_throw_exception_if_customer_with_provided_id_no_exist() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        Customer newCustomer = customers.get(1);

        String message = "Customer with id: 1 no exist!";

        Assertions.assertThrows(
                NoSuchElementException.class, () -> service.update(1L, newCustomer), message);
    }

    @Test
    void update_should_call_updateId_method_if_updated_customer_have_other_id() {
        when(repository.findById(1L)).thenReturn(Optional.of(customers.get(0)));
        when(repository.findById(3L)).thenReturn(Optional.of(customers.get(0)));

        Customer newCustomer = customers.get(2);
        newCustomer.setEmail(customers.get(0).getEmail());

        service.update(1L, newCustomer);

        verify(repository, times(1)).updateId(1L, 3L);


    }

    @Test
    void update_should_throw_exception_if_updated_customer_have_other_email() {
        when(repository.findById(1L)).thenReturn(Optional.of(customers.get(0)));

        Customer newCustomer = customers.get(2);

        Assertions.assertThrows(EmailEditException.class, () -> service.update(1L, newCustomer));

    }

    @Test
    void delete_throw_exception_if_customer_with_provided_id_no_exist() {
        when(repository.findById(1L)).thenReturn(Optional.empty());

        String message = "Customer with id: 1 no exist!";

        Assertions.assertThrows(
                NoSuchElementException.class, () -> service.delete(1L), message);
    }


    private List<Customer> createListOfCustomers() {

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
}