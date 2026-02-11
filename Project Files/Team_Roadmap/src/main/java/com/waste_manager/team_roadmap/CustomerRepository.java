package com.waste_manager.team_roadmap;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface CustomerRepository extends Repository<Customer, Long> {

    Customer save(Customer customer);

    // Find All
    List<Customer> findAll();

    // Find By
    Optional<Customer> findById(long id);
    List<Customer> findByDName(String dName);
    List<Customer> findByStreakGreaterThan(int streak);

    // update
    @Modifying
    @Query("update Customer u set u.dName = ?1 where u.ID = ?2")
    void updateDNameById(String newDName, long id);
    @Modifying
    @Query("update Customer u set u.fName = ?1 where u.ID = ?2")
    void updateFNameById(String newFName, long id);
    @Modifying
    @Query("update Customer u set u.sName = ?1 where u.ID = ?2")
    void updateSNameById(String newSName, long id);

    @Modifying
    @Query("update Customer u set u.streak = ?1 where u.ID = ?2")
    void updateStreakById(int newStreak, long id);

    @Modifying
    @Query("update Customer u set u.address = ?1 where u.ID = ?2")
    void updateAddressById(String newAddress, long id);
    @Modifying
    @Query("update Customer u set u.postcode = ?1 where u.ID = ?2")
    void updatePostcodeById(String newPostcode, long id);
    @Modifying
    @Query("update Customer u set u.county = ?1 where u.ID = ?2")
    void updateCountyById(String newCounty, long id);

    @Modifying
    @Query("update Customer u set u.email = ?1 where u.ID = ?2")
    void updateEmailById(String newEmail, long id);
    @Modifying
    @Query("update Customer u set u.phone = ?1 where u.ID = ?2")
    void updatePhoneById(String newPhone, long id);
    @Modifying
    @Query("update Customer u set u.password = ?1 where u.ID = ?2")
    void updatePasswordById(String newPassword, long id);

}
