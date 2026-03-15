package com.waste_manager.team_roadmap;

import org.springframework.data.repository.Repository;

public interface AdminRepository extends Repository<Admin, Long> {
    Admin save(Admin admin);

    // TODO: Getters and setters
}
