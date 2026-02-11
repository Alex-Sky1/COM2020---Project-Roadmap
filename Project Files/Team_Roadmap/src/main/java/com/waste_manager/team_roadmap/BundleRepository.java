package com.waste_manager.team_roadmap;

import org.springframework.data.jpa.repository.Modifying;
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
    @Modifying
    @Query("update Bundle b set b.reserved=?1 where b.id=?2")
    void setBundleReserved(boolean reserved, long id);
    @Modifying
    @Query("update Bundle b set b.expired=true where b.id=?1")
    void setBundleExpired(long id);
}