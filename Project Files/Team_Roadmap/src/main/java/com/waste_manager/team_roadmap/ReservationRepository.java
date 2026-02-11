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

    @Transactional
    @Modifying
    @Query("update Reservation r set r.status = :status where r.ID = :ID")
    void setReservationStatus(@Param("status") String newStatus, @Param("ID") long id);

}
