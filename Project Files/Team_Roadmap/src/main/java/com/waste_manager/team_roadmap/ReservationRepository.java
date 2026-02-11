package com.waste_manager.team_roadmap;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.List;

public interface ReservationRepository extends Repository<Reservation, Long> {

    Reservation save(Reservation reservation);

    List<Reservation> findByCustomerID(long customer_id);

    @Modifying
    @Query("update Respository r set r.status = ?1 where r.id = ?2")
    void setReservationStatus(String newStatus, long id);

}
