package com.waste_manager.team_roadmap;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class IssueReportTest {

    static IssueReport testIssueReport;

    // Ran once before any of the tests
    @BeforeEach
    public void setup(){
        testIssueReport = new IssueReport(1, 1, "complaint", "Undelivered Food",
                                        false, "");
    }

    // Verifies the ID of a bundle
    @Test
    public void testGetIssueID() {

        assertEquals(0, testIssueReport.getIssueID());
    }

    @Test
    public void testGetPostingID() {

        assertEquals(1, testIssueReport.getPostingID());
    }

    @Test
    public void testGetCustomerID() {

        assertEquals(1, testIssueReport.getCustomerID());
    }

    @Test
    public void testGetType() {

        assertSame("complaint", testIssueReport.getType());
    }

    @Test
    public void testGetDescription() {

        assertSame("Undelivered Food", testIssueReport.getDescription());
    }

    @Test
    public void testGetFixed() {

        assertFalse(testIssueReport.getFixed());
    }

    @Test
    public void testGetSellerResponse() {

        assertSame("", testIssueReport.getSellerResponse());
    }


    /// //////////////////////////////////////////////////////////////

    @Test
    public void testSetIssueID() {

        testIssueReport.setIssueID(3);
        assertEquals(3, testIssueReport.getIssueID());
    }

    @Test
    public void testSetPostingID() {

        testIssueReport.setPostingID(3);
        assertEquals(3, testIssueReport.getPostingID());
    }

    @Test
    public void testSetCustomerID() {

        testIssueReport.setCustomerID(3);
        assertEquals(3, testIssueReport.getCustomerID());
    }

    // Verifies the ID of the bundle seller
    @Test
    public void testSetType() {

        testIssueReport.setType("timmy");
        assertSame("timmy", testIssueReport.getType());
    }

    // Verifies the category of the bundle
    @Test
    public void testSetDescription() {

        testIssueReport.setDescription("NA");
        assertSame("NA", testIssueReport.getDescription());
    }

    @Test
    public void testSetFixed() {

        testIssueReport.setFixed(false);
        assertSame(false, testIssueReport.getFixed());
    }

    @Test
    public void testSetSellerResponse() {

        testIssueReport.setSellerResponse("?");
        assertSame("?", testIssueReport.getSellerResponse());
    }


}
