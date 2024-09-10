package guru.springframework.spring6restmvc.repositories;

import guru.springframework.spring6restmvc.entities.Customer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, UUID> {


    Page<Customer> findByNameContaining(String name, Pageable pageable);

    Page<Customer> findByEmailContaining(String email, Pageable pageable);


    Page<Customer> findByNameContainingAndEmailContaining(String name, String email, Pageable pageable);
}
