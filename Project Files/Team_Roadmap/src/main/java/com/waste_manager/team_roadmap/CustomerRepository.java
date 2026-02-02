package com.waste_manager.team_roadmap;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface CustomerRepository extends Repository<Customer, Long> {

    Customer save(Customer customer);

    Page<Customer> findAll(Pageable pageable);
    Optional<Customer> findById(long id);


}
