package com.waste_manager.team_roadmap;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

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
    @Transactional
    @Modifying
    @Query("update Customer u set u.dName = :DName where u.ID = :ID")
    void updateDNameById(@Param("DName") String newDName, @Param("ID") long id);
    @Transactional
    @Modifying
    @Query("update Customer u set u.fName = :FName where u.ID = :ID")
    void updateFNameById(@Param("FName") String newFName, @Param("ID") long id);
    @Transactional
    @Modifying
    @Query("update Customer u set u.sName = :SName where u.ID = :ID")
    void updateSNameById(@Param("SName") String newSName, @Param("ID") long id);

    @Transactional
    @Modifying
    @Query("update Customer u set u.streak = :streak where u.ID = :ID")
    void updateStreakById(@Param("streak") int newStreak, @Param("ID") long id);

    @Transactional
    @Modifying
    @Query("update Customer u set u.address = :address where u.ID = :ID")
    void updateAddressById(@Param("address") String newAddress, @Param("ID") long id);
    @Transactional
    @Modifying
    @Query("update Customer u set u.postcode = :postcode where u.ID = :ID")
    void updatePostcodeById(@Param("postcode") String newPostcode, @Param("ID") long id);
    @Transactional
    @Modifying
    @Query("update Customer u set u.county = :county where u.ID = :ID")
    void updateCountyById(@Param("county") String newCounty, @Param("ID") long id);

    @Transactional
    @Modifying
    @Query("update Customer u set u.email = :email where u.ID = :ID")
    void updateEmailById(@Param("email") String newEmail, @Param("ID") long id);
    @Transactional
    @Modifying
    @Query("update Customer u set u.phone = :phone where u.ID = :ID")
    void updatePhoneById(@Param("phone") String newPhone, @Param("ID") long id);
    @Transactional
    @Modifying
    @Query("update Customer u set u.password = :password where u.ID = :ID")
    void updatePasswordById(@Param("password") String newPassword, @Param("ID") long id);

}
