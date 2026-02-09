package com.waste_manager.team_roadmap;

import org.springframework.data.jpa.repository.Query;
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
    List<Bundle> findBySellerID(long sellerId);
    List<Bundle> findByExpiredTrueAndSellerID(long sellerId);
    List<Bundle> findByReservedAndExpired(boolean reserved, boolean expired);

    // Order
//    List<Bundle> findAllOrderByPrice();
//    List<Bundle> findAllOrderByDiscount();

    // updates
    @Query("update Bundle set reserved=true where id=?1")
    Optional<Bundle> setBundleReserved(long id);
    @Query("update Bundle set expired=true where id=?1")
    Optional<Bundle> setBundleExpired(long id);
}