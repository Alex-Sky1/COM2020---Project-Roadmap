package com.waste_manager.team_roadmap;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface SellerRepository extends Repository<Seller, Long> {

    Seller save(Seller seller);

    // Find All
    List<Seller> findAll();

    // Find By
    Optional<Seller> findById(long id);
    List<Seller> findByDName(String dName);

    // Update
    @Modifying
    @Query("update Seller u set u.dName = ?1 where u.id = ?2")
    Optional<Seller> updateDNameById(String newDName, long id);
    @Modifying
    @Query("update Seller u set u.fName = ?1 where u.id = ?2")
    Optional<Seller> updateFNameById(String newFName, long id);
    @Modifying
    @Query("update Seller u set u.sName = ?1 where u.id = ?2")
    Optional<Seller> updateSNameById(String newSName, long id);

    @Modifying
    @Query("update Seller u set u.address = ?1 where u.id = ?2")
    Optional<Seller> updateAddressById(String newAddress, long id);
    @Modifying
    @Query("update Seller u set u.postcode = ?1 where u.id = ?2")
    Optional<Seller> updatePostcodeById(String newPostcode, long id);
    @Modifying
    @Query("update Seller u set u.county = ?1 where u.id = ?2")
    Optional<Seller> updateCountyById(String newCounty, long id);

    @Modifying
    @Query("update Seller u set u.email = ?1 where u.id = ?2")
    Optional<Seller> updateEmailById(String newEmail, long id);
    @Modifying
    @Query("update Seller u set u.phone = ?1 where u.id = ?2")
    Optional<Seller> updatePhoneById(String newPhone, long id);
}