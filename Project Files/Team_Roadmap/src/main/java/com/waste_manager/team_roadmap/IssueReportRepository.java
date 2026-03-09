package com.waste_manager.team_roadmap;

import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface IssueReportRepository extends Repository<IssueReport, Long> {

    IssueReport save(IssueReport report);
    List<IssueReport> findAll();

    Optional<IssueReport> findById(long id);
    List<IssueReport> findByBundleID(long bundle_id);
    List<IssueReport> findByCustomerID(long customer_id);

    List<IssueReport> findByType(String type);
    List<IssueReport> findByResolved(boolean resolved);

}
