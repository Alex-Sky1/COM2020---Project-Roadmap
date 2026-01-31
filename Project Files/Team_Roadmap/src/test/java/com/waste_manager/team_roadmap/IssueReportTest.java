package com.waste_manager.team_roadmap;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class IssueReportTest {

    static IssueReport testIssueReport;

    // Ran once before any of the tests
    @BeforeAll
    public static void setup() throws IllegalPackSizeException {
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

        assertTrue(testIssueReport.getFixed() == true);
    }

    @Test
    public void testGetSellerResponse() {

        assertSame("come back and I will sort out your order", testIssueReport.getSellerResponse());
    }


    /// //////////////////////////////////////////////////////////////

    @Test
    public void testSetIssueID() {

        testBundle.setIssueID(3);
        assertTrue(testBundle.getIssueID() == 3);
    }

    @Test
    public void testSetPostingID() {

        testBundle.setPostingID(3);
        assertTrue(testBundle.getPostingID() == 3);
    }

    @Test
    public void testSetCustomerID() {

        testBundle.setCustomerID(3);
        assertTrue(testBundle.getCustomerID() == 3);
    }

    // Verifies the ID of the bundle seller
    @Test
    public void testSetType() {

        testBundle.setType("timmy");
        assertSame("timmy", testBundle.getType());
    }

    // Verifies the category of the bundle
    @Test
    public void testSetDescription() {

        testBundle.setDescription("NA");
        assertSame("NA", testBundle.getDescription());
    }


    @Test
    public void testSetSellerResponse() {

        testBundle.setSellerResponse("?");
        assertSame("?", testBundle.getSellerResponse());
    }


}
