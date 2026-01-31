package com.waste_manager.team_roadmap;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;

public class ReservationTest {

    // Ran once before any of the tests
    @BeforeAll
    public static void setup() throws IllegalPackSizeException {
        Reservation testReservation = new Reservation(); // Constructor doesn't exist currently
    }

    // Verifies the ID of a bundle
    @Test
    public void testGetReservationID() {

        Assert.assertTrue(testReservation.getReservationID == 1);
    }

    @Test
    public void testGetPostingID() {

        Assert.assertTrue(testReservation.getPostingID == 1);
    }

    @Test
    public void testGetCustomerID() {

        Assert.assertTrue(testReservation.getCustomerID == 1);
    }

    @Test
    public void testGetTimestamp() {

        Assert.assertTrue(testReservation.getTimestamp == "01/01/2026");
    }

    @Test
    public void testGetClaimCode() {

        Assert.assertTrue(testReservation.getClaimCode == "PLACEHOLDER");
    }

    @Test
    public void testGetStatus() {

        Assert.assertTrue(testReservation.getStatus == "Claimed");
    }

    @Test
    public void testGetWeatherFlag() {

        Assert.assertTrue(testReservation.getWeatherFlag == "Raining");
    }

    /////////////////////////////////////////////////////////


    @Test
    public void testSetReservationID() {

        testReservation.setReservationID(3);
        Assert.assertTrue(testReservation.getReservationID == 3);
    }

    @Test
    public void testSetPostingID() {

        testReservation.setPostingID(3);
        Assert.assertTrue(testReservation.getPostingID == 3);
    }

    @Test
    public void testSetCustomerID() {

        testReservation.setCustomerID(3);
        Assert.assertTrue(testReservation.getCustomerID == 3);
    }

    @Test
    public void testSetTimestamp() {

        testReservation.setTimestamp("02/02/2026");
        Assert.assertTrue(testReservation.getTimestamp == "02/02/2026");
    }

    @Test
    public void testSetClaimCode() {

        testReservation.setClaimCode("ANOTHER_PLACEHOLDER");
        Assert.assertTrue(testReservation.getClaimCode == "ANOTHER_PLACEHOLDER");
    }

    @Test
    public void testSetStatus() {

        testReservation.setStatus("Unclaimed");
        Assert.assertTrue(testReservation.getStatus == "Unclaimed");
    }

    @Test
    public void testSetWeatherFlag() {

        testReservation.setWeatherFlag("Sunny");
        Assert.assertTrue(testReservation.getWeatherFlag == "Sunny");
    }
}
