package com.waste_manager.team_roadmap.tests;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
        BundleTest.class,
        CustomerTest.class,
        IssueReportTest.class,
        SellerTest.class,
        ReservationTest.class,
        ForecastTest.class,
        LoginControllerTest.class
})
public class TeamRoadmapTestSuite {}