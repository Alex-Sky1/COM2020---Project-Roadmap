package com.waste_manager.team_roadmap;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface BundleRepository extends Repository<Bundle, Long> {

    Bundle save(Bundle bundle);

    // Find All
    List<Bundle> findAll();

    // Find By
    Optional<Bundle> findById(long id);
    List<Bundle> findByReserved(boolean Reserved);
    List<Bundle> findByExpired(boolean Expired);

    // Order
    List<Bundle> findAllOrderByPrice();
    List<Bundle> findAllOrderByDiscount();
}