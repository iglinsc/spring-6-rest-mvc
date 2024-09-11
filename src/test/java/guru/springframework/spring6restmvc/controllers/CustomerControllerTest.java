package guru.springframework.spring6restmvc.controllers;

import guru.springframework.spring6restmvc.model.CustomerDTO;
import guru.springframework.spring6restmvc.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class CustomerControllerTest {

    @Mock
    private CustomerService customerService;

    @InjectMocks
    private CustomerController customerController;

    private CustomerDTO customerDTO;
    private UUID customerId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        customerId = UUID.randomUUID();
        customerDTO = new CustomerDTO(
                customerId,
                "John Doe",
                "john.doe@example.com",
                1,
                null,
                null
        );
    }

    @Test
    void updateCustomerPatchById_Success() {
        doNothing().when(customerService).patchCustomerById(any(UUID.class), any(CustomerDTO.class));
        ResponseEntity<Void> response = customerController.updateCustomerPatchById(customerId, customerDTO);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void deleteById_Success() {
        when(customerService.deleteById(any(UUID.class))).thenReturn(true);
        ResponseEntity<Void> response = customerController.deleteById(customerId);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    void deleteById_NotFound() {
        when(customerService.deleteById(any(UUID.class))).thenReturn(false);
        assertThrows(NotFoundException.class, () -> customerController.deleteById(customerId));
    }

    @Test
    void createCustomer_Success() throws URISyntaxException {
        when(customerService.saveCustomer(any(CustomerDTO.class))).thenReturn(customerDTO);
        ResponseEntity<CustomerDTO> response = customerController.createCustomer(customerDTO);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(new URI("/api/v1/customer/" + customerId.toString()), response.getHeaders().getLocation());
    }

    @Test
    void updateById_Success() {
        when(customerService.updateById(any(UUID.class), any(CustomerDTO.class))).thenReturn(Optional.of(customerDTO));
        ResponseEntity<Void> response = customerController.updateById(customerId, customerDTO);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
    @Test
    void updateById_NotFound() {
        when(customerService.updateById(any(UUID.class), any(CustomerDTO.class))).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> customerController.updateById(customerId, customerDTO));
    }





    @Test
    void getCustomerById_Success() {
        when(customerService.getCustomerById(any(UUID.class))).thenReturn(Optional.of(customerDTO));
        CustomerDTO response = customerController.getCustomerById(customerId);
        assertEquals(customerDTO, response);
    }

    @Test
    void getCustomerById_NotFound() {
        when(customerService.getCustomerById(any(UUID.class))).thenReturn(Optional.empty());
        assertThrows(RuntimeException.class, () -> customerController.getCustomerById(customerId));
    }
}
