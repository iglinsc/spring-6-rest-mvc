package guru.springframework.spring6restmvc.mappers;

import guru.springframework.spring6restmvc.entities.Customer;
import guru.springframework.spring6restmvc.model.CustomerDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface CustomerMapper {

    Customer customerDtoToCustomer(CustomerDTO dto);

    @Mapping(target = "createdDate", source = "createdDate")
    @Mapping(target = "updateDate", source = "updateDate")
    CustomerDTO customerToCustomerDto(Customer customer);
}
