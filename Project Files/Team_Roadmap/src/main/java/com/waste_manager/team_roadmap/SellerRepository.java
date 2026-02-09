package com.waste_manager.team_roadmap;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface SellerRepository extends Repository<Seller, Long> {

    Seller save(Seller seller);

    // Find All
    List<Seller> findAll();

    // Find By
    Optional<Seller> findById(long id);
    List<Seller> findByName(String name);

    // Update
    Optional<Seller> updateDNameById(String newDName, long id);
    Optional<Seller> updateFNameById(String newFName, long id);
    Optional<Seller> updateSNameById(String newSName, long id);

    Optional<Seller> updateAddressById(String newAddress, long id);
    Optional<Seller> updatePostcodeById(String newPostcode, long id);
    Optional<Seller> updateCountryById(String newCountry, long id);

    Optional<Seller> updateEmailById(String newEmail, long id);
    Optional<Seller> updatePhoneById(String newPhone, long id);
}