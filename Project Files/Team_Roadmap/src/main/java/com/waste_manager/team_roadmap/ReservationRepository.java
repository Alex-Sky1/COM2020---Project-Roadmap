package com.waste_manager.team_roadmap;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository extends Repository<Reservation, Long> {

    Reservation save(Reservation reservation);
    List<Reservation> findAll();

    // find by queries
    Optional<Reservation> findById(long id);
    List<Reservation> findByCustomerID(long customer_id);
    List<Reservation> findBySellerID(long seller_id);
    List<Reservation> findByBundleID(long bundle_id);
    List<Reservation> findByCollected(boolean collected);
    List<Reservation> findByNoShow(boolean noShow);

    @Query("select r.bundle from Reservation r where r.ID = :ID")
    List<Bundle> getCustomerReservedBundles(@Param("ID") long customer_id);

    @Transactional
    @Modifying
    @Query("update Reservation r set r.collected = :collected where r.ID = :ID")
    void setReservationCollected(@Param("collected") boolean newCollected, @Param("ID") long id);

    @Transactional
    @Modifying
    @Query("update Reservation r set r.noShow = :noShow where r.ID = :ID")
    void setReservationNoShow(@Param("noShow") boolean newNoShow, @Param("ID") long id);


    // count queries
    long countByCustomerID(long customer_id);
    long countBySellerID(long seller_id);
    long countByCollected(boolean collected);
    long countByNoShow(boolean noShow);
}
