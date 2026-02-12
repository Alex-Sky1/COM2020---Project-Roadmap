package com.waste_manager.team_roadmap;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ReservationRepository extends Repository<Reservation, Long> {

    Reservation save(Reservation reservation);

    List<Reservation> findByCustomerID(long customer_id);
    List<Reservation> findBySellerID(long seller_id);

    //@Query("select r.bundle from Reservation r where r.customer_id = :ID")
    //List<Bundle> getCustomerReservedBundles(@Param("ID") long customer_id);


    @Transactional
    @Modifying
    @Query("update Reservation r set r.collected = :status where r.ID = :ID")
    void setReservationStatus(@Param("collected") boolean newCollected, @Param("ID") long id);

}
