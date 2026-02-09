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
    Optional<Seller> updateNameById(String newName, long id);
    Optional<Seller> updateContactStubById(String newContactStub, long id);
}