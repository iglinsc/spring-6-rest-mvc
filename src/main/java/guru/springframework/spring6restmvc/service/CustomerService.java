package guru.springframework.spring6restmvc.service;

import guru.springframework.spring6restmvc.model.CustomerDTO;
import org.springframework.data.domain.Page;

import java.util.Optional;
import java.util.UUID;

public interface CustomerService {

    Page<CustomerDTO> listCustomers(String name, String email, Integer pageNumber, Integer pageSize);

    Optional<CustomerDTO> getCustomerById(UUID id);

    CustomerDTO saveCustomer(CustomerDTO customerDTO);

    Optional<CustomerDTO> updateById(UUID id, CustomerDTO customerDTO);

    boolean deleteById(UUID id);

    void patchCustomerById(UUID id, CustomerDTO customerDTO);
}
