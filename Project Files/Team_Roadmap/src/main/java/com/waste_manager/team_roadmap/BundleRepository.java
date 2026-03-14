package com.waste_manager.team_roadmap;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

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

    // Updates
    @Transactional
    @Modifying
    @Query("update Bundle b set b.reserved = :reserved where b.ID = :ID")
    void setBundleReserved(@Param("reserved") boolean reserved, @Param("ID") long id);
    @Transactional
    @Modifying
    @Query("update Bundle b set b.expired = true where b.ID = :ID")
    void setBundleExpired(@Param("ID") long id);

    // count queries
    long countBySellerID(long seller_id);
    long countByReserved(boolean reserved);
    long countByExpired(boolean expired);
}