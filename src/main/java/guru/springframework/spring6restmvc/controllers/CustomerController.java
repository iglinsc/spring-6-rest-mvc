package guru.springframework.spring6restmvc.controllers;

import guru.springframework.spring6restmvc.entities.Customer;
import guru.springframework.spring6restmvc.model.CustomerDTO;
import guru.springframework.spring6restmvc.service.CustomerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@RestController
@RequestMapping("api/v1/customer")
public class CustomerController {

    private final CustomerService customerService;

    @PatchMapping("/{id}")
    public ResponseEntity<Void> updateCustomerPatchById(@PathVariable("id") UUID id, @RequestBody CustomerDTO customerDTO) {
        customerService.patchCustomerById(id, customerDTO);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable("id") UUID id) {
        if (!customerService.deleteById(id)) {
            throw new NotFoundException("Customer with id " + id + " not found");
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping
    public ResponseEntity<CustomerDTO> createCustomer(@Validated @RequestBody CustomerDTO customerDTO) throws URISyntaxException {
        CustomerDTO savedCustomer = customerService.saveCustomer(customerDTO);
        HttpHeaders headers = new HttpHeaders();
        headers.setLocation(new URI("/api/v1/customer/" + savedCustomer.getId().toString()));
        return new ResponseEntity<>(savedCustomer, headers, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateById(@PathVariable("id") UUID id, @RequestBody CustomerDTO customerDTO) {
        if (customerService.updateById(id, customerDTO).isEmpty()) {
            throw new NotFoundException("Customer with id " + id + " not found");
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<Page<CustomerDTO>> listCustomers(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) Integer pageNumber,
            @RequestParam(required = false) Integer pageSize) {
        Page<CustomerDTO> customers = customerService.listCustomers(name, email, pageNumber, pageSize);
        return ResponseEntity.ok(customers);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public CustomerDTO getCustomerById(@PathVariable("id") UUID id) {
        log.debug("Getting customer by id {}", id);
        return customerService.getCustomerById(id).orElseThrow(() -> new RuntimeException("Customer not found"));
    }
}