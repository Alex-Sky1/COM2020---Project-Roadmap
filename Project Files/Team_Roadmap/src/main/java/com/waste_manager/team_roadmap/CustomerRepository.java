package com.waste_manager.team_roadmap;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends Repository<Customer, Long> {

    Customer save(Customer customer);

    // Find All
    List<Customer> findAll();

    // Find By
    Optional<Customer> findById(long id);
    List<Customer> findByDisplayName(String name);
    List<Customer> findByStreakGreaterThan(int streak);

    // update
    Optional<Customer> updateDNameById(String newDName, long id);
    Optional<Customer> updateFNameById(String newFName, long id);
    Optional<Customer> updateSNameById(String newSName, long id);

    Optional<Customer> updateStreakById(int newStreak, long id);

    Optional<Customer> updateAddressById(String newAddress, long id);
    Optional<Customer> updatePostcodeById(String newPostcode, long id);
    Optional<Customer> updateCountryById(String newCountry, long id);

    Optional<Customer> updateEmailById(String newEmail, long id);
    Optional<Customer> updatePhoneById(String newPhone, long id);

}
