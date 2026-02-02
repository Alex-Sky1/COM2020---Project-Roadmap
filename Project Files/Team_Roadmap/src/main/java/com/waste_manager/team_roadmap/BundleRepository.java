package com.waste_manager.team_roadmap;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.Repository;

import java.util.Optional;

public interface BundleRepository extends Repository<Bundle, Long> {

    Bundle save(Bundle bundle);

    Page<Bundle> findAll(Pageable pageable);
    Optional<Bundle> findById(long id);
}