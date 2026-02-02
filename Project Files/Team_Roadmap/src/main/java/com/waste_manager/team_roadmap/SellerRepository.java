package com.waste_manager.team_roadmap;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface SellerRepository extends Repository<Seller, Long> {

    Seller save(Seller seller);

    Page<Seller> findAll(Pageable pageable);
    Optional<Seller> findById(long id);
}