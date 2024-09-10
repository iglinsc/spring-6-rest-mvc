package guru.springframework.spring6restmvc.service;

import guru.springframework.spring6restmvc.entities.Customer;
import guru.springframework.spring6restmvc.mappers.CustomerMapper;
import guru.springframework.spring6restmvc.model.CustomerDTO;
import guru.springframework.spring6restmvc.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerServiceImpl implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Autowired
    public CustomerServiceImpl(CustomerRepository customerRepository, CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    @Override
    public Page<CustomerDTO> listCustomers(String name, String email, Integer pageNumber, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNumber != null ? pageNumber : 0, pageSize != null ? pageSize : 10);

        Page<Customer> customerPage;
        if (name != null && email != null) {
            customerPage = customerRepository.findByNameContainingAndEmailContaining(name, email, pageable);
        } else if (name != null) {
            customerPage = customerRepository.findByNameContaining(name, pageable);
        } else if (email != null) {
            customerPage = customerRepository.findByEmailContaining(email, pageable);
        } else {
            customerPage = customerRepository.findAll(pageable);
        }

        return customerPage.map(customerMapper::customerToCustomerDto);
    }

    @Override
    public Optional<CustomerDTO> getCustomerById(UUID id) {
        return customerRepository.findById(id).map(customerMapper::customerToCustomerDto);
    }

    @Override
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        Customer customer = customerMapper.customerDtoToCustomer(customerDTO);
        customer.setCreatedDate(LocalDateTime.now());
        customer.setUpdateDate(LocalDateTime.now());
        Customer savedCustomer = customerRepository.save(customer);
        return customerMapper.customerToCustomerDto(savedCustomer);
    }

    @Override
    public Optional<CustomerDTO> updateById(UUID id, CustomerDTO customerDTO) {
        if (customerRepository.existsById(id)) {
            Customer customer = customerMapper.customerDtoToCustomer(customerDTO);
            customer.setId(id);
            customer.setUpdateDate(LocalDateTime.now());
            Customer updatedCustomer = customerRepository.save(customer);
            return Optional.of(customerMapper.customerToCustomerDto(updatedCustomer));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public boolean deleteById(UUID id) {
        if (customerRepository.existsById(id)) {
            customerRepository.deleteById(id);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void patchCustomerById(UUID id, CustomerDTO customerDTO) {
        customerRepository.findById(id).ifPresent(existingCustomer -> {
            if (customerDTO.getName() != null) {
                existingCustomer.setName(customerDTO.getName());
            }
            if (customerDTO.getEmail() != null) {
                existingCustomer.setEmail(customerDTO.getEmail());
            }
            // Add other fields you want to update in the patch
            existingCustomer.setUpdateDate(LocalDateTime.now());
            customerRepository.save(existingCustomer);
        });
    }
}
