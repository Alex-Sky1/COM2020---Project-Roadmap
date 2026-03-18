package com.waste_manager.team_roadmap;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface AdminRepository extends Repository<Admin, Long> {
    Admin save(Admin admin);

    @Query("select a from Admin a where a.id = 1")
    Admin getAdmin();

    Optional<Admin> findByDName(String dName);

    // TODO: Getters and setters

    @Query("select a.customerView from Admin a where a.ID = 1")
    Customer getAdminCustomerAccount();
}
