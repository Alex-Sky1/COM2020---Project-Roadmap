package com.waste_manager.team_roadmap.tests;

import com.waste_manager.team_roadmap.Bundle;
import com.waste_manager.team_roadmap.Customer;
import com.waste_manager.team_roadmap.IssueReport;
import com.waste_manager.team_roadmap.Seller;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


public class IssueReportTest {

    static IssueReport testIssueReport;
    static Customer testCustomer;
    static Bundle testBundle;
    static Seller testSeller;
    static LocalDateTime testTime = LocalDateTime.of(2026, 2, 1, 12, 0);

    // Ran once before any of the tests
    @BeforeEach
    public void setup(){

        testSeller = new Seller("Peter", "Pan", "Pete's Pancakes", "Neverland", "NV21 TK2", "Crocodile Creek",
                "Peter.Pan12@hookmail.com", "06847 268425", "T1nkerb3ll!", true);
        testTime = LocalDateTime.of(2026, 2, 1, 12, 0);
        testCustomer = new Customer("Robin", "Hood", "RobOfNotts1364", "Sherwood Forest", "NG21 9RM", "Nottinghamshire",
                "rHood1334@ping,com", "07862 561843", "L1tt!e John", 1, new ArrayList<>(List.of(false, false, false, false, false)), true);
        testBundle = new Bundle(testSeller, "puddings", new ArrayList<>(List.of("Cake", "Brownie")),
                new ArrayList<>(List.of("Cake", "Brownie")), testTime, 7.50f, 10, 12,
                true, false, "sunny");

        testIssueReport = new IssueReport(testBundle, testCustomer, "complaint", "Undelivered Food",
                                        false, "");
    }

    @Test
    public void testGetIssueID() {assertEquals(0, testIssueReport.getIssueID());}

    @Test
    public void testSetIssueID() {
        testIssueReport.setIssueID(3);
        assertEquals(3, testIssueReport.getIssueID());
    }


    @Test
    public void testGetBundle() {assertEquals(testBundle, testIssueReport.getBundle());}

    @Test
    public void testSetBundle() {
        Bundle newTestBundle = new Bundle(testSeller, "puddings", new ArrayList<>(List.of("Cake", "Brownie")),
                new ArrayList<>(List.of("Cake", "Brownie", "chocolate")), testTime, 7.50f, 10, 12,
                true, false, "sunny");
        testIssueReport.setBundle(newTestBundle);
        assertEquals(newTestBundle, testIssueReport.getBundle());
    }


    @Test
    public void testGetCustomer() {assertEquals(testCustomer, testIssueReport.getCustomer());}

    @Test
    public void testSetCustomerID() {
        Customer newTestCustomer = new Customer("Robin", "Hood", "RobOfNotts1364", "Sherwood Forest", "NG21 9RM", "Nottinghamshire",
                "rHood1334@ping,com", "07862 125937", "L1tt!e John", 1, new ArrayList<>(List.of(false, false, false, false, false)), true);
        testIssueReport.setCustomer(newTestCustomer);
        assertEquals(newTestCustomer, testIssueReport.getCustomer());
    }


    @Test
    public void testGetType() {assertSame("complaint", testIssueReport.getType());}

    @Test
    public void testSetType() {
        testIssueReport.setType("timmy");
        assertSame("timmy", testIssueReport.getType());
    }


    @Test
    public void testGetDescription() {assertSame("Undelivered Food", testIssueReport.getDescription());}

    @Test
    public void testSetDescription() {
        testIssueReport.setDescription("NA");
        assertSame("NA", testIssueReport.getDescription());
    }


    @Test
    public void testGetResolvedStatus() {assertFalse(testIssueReport.getResolved());}

    @Test
    public void testSetResolvedStatus() {
        testIssueReport.setResolved(true);
        assertSame(true, testIssueReport.getResolved());
    }


    @Test
    public void testGetSellerResponse() {assertSame("", testIssueReport.getSellerResponse());}

    @Test
    public void testSetSellerResponse() {
        testIssueReport.setSellerResponse("yes");
        assertSame("yes", testIssueReport.getSellerResponse());
    }
}
