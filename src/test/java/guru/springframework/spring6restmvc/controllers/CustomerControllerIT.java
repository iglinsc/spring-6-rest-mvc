package guru.springframework.spring6restmvc.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.spring6restmvc.model.CustomerDTO;
import guru.springframework.spring6restmvc.service.CustomerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class CustomerControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private CustomerService customerService;

    private UUID customerId;
    private CustomerDTO customerDTO;

    @BeforeEach
    void setUp() {
        customerId = UUID.randomUUID();
        customerDTO = CustomerDTO.builder()
                .id(customerId)
                .name("John Doe")
                .email("john.doe@example.com")
                .version(1)
                .createdDate(LocalDateTime.now())
                .updateDate(LocalDateTime.now())
                .build();
    }

    @Test
    void createCustomer_Success() throws Exception {
        when(customerService.saveCustomer(any(CustomerDTO.class))).thenReturn(customerDTO);

        mockMvc.perform(post("/api/v1/customer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerDTO)))
                .andExpect(status().isCreated())
                .andExpect(header().string("Location", "/api/v1/customer/" + customerId.toString()))
                .andExpect(jsonPath("$.id").value(customerId.toString()))
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    void getCustomerById_Success() throws Exception {
        when(customerService.getCustomerById(eq(customerId))).thenReturn(Optional.of(customerDTO));

        mockMvc.perform(get("/api/v1/customer/" + customerId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(customerId.toString()))
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    void getCustomerById_NotFound() throws Exception {
        UUID customerId = UUID.randomUUID();  // Ensure customerId is initialized

        // Mock the behavior of customerService to return an empty Optional when customer is not found
        when(customerService.getCustomerById(eq(customerId))).thenReturn(Optional.empty());

        // Perform the GET request to check if the customer is not found
        mockMvc.perform(get("/api/v1/customer/" + customerId))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof RuntimeException))
                .andExpect(result -> assertEquals("Customer not found", result.getResolvedException().getMessage()));
    }



    @Test
    void updateCustomerPatchById_Success() throws Exception {
        mockMvc.perform(patch("/api/v1/customer/" + customerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerDTO)))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteById_Success() throws Exception {
        when(customerService.deleteById(eq(customerId))).thenReturn(true);

        mockMvc.perform(delete("/api/v1/customer/" + customerId))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteById_NotFound() throws Exception {
        when(customerService.deleteById(eq(customerId))).thenReturn(false);

        mockMvc.perform(delete("/api/v1/customer/" + customerId))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NotFoundException))
                .andExpect(result -> assertEquals("Customer with id " + customerId + " not found", result.getResolvedException().getMessage()));
    }

    @Test
    void listCustomers_Success() throws Exception {
        Page<CustomerDTO> page = new PageImpl<>(Collections.singletonList(customerDTO), PageRequest.of(0, 1), 1);
        when(customerService.listCustomers(anyString(), anyString(), any(Integer.class), any(Integer.class))).thenReturn(page);

        mockMvc.perform(get("/api/v1/customer")
                        .param("pageNumber", "0")
                        .param("pageSize", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(customerId.toString()))
                .andExpect(jsonPath("$.content[0].name").value("John Doe"));
    }

    @Test
    void updateById_NotFound() throws Exception {
        when(customerService.updateById(any(UUID.class), any(CustomerDTO.class))).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/v1/customer/" + customerId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerDTO)))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof NotFoundException))
                .andExpect(result -> assertEquals("Customer with id " + customerId + " not found", result.getResolvedException().getMessage()));
    }
}
