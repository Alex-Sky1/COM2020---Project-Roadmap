package com.waste_manager.team_roadmap;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class IssueReportTest {

    static IssueReport testIssueReport;

    // Ran once before any of the tests
    @BeforeAll
    public static void setup(){
        testIssueReport = new IssueReport(); // Constructor doesn't exist currently
    }

    // Verifies the ID of a bundle
    @Test
    public void testGetIssueID() {

        assertEquals(1, testIssueReport.getIssueID());
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

        assertSame("missing item", testIssueReport.getType());
    }

    @Test
    public void testGetDescription() {

        assertSame("A bit Fishy", testIssueReport.getDescription());
    }

    @Test
    public void testGetFixed() {

        assertTrue(testIssueReport.getFixed());
    }

    @Test
    public void testGetSellerResponse() {

        assertSame("come back and I will sort out your order", testIssueReport.getSellerResponse());
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
