package com.waste_manager.team_roadmap_tests;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
        BundleTest.class,
        CustomerTest.class,
        IssueReportTest.class,
        SellerTest.class,
        ReservationTest.class,
        ForecastTest.class
})
public class TeamRoadmapTestSuite {
}