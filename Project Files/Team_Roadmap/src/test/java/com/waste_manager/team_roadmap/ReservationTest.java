package com.waste_manager.team_roadmap;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

public class ReservationTest {

    // Initialising the test instance to allow unit tests to be run
    static Reservation testReservation;

    // Ran once before any of the tests
    @BeforeAll
    public static void setup() {
        testReservation = new Reservation(1, 1, 1,
                                            LocalDateTime.of(2026, 2, 1, 12, 0),
                                            "A4RDW", "Unclaimed", "Rainy");
    }

    // Verifies the ID of a reservation
    @Test
    public void testGetReservationID() {

        assertEquals(1, testReservation.getReservationID());
    }

    // Verifies the Posting ID of a reservation
    @Test
    public void testGetPostingID() {

        assertEquals(1, testReservation.getPostingID());
    }

    // Verifies the customer ID of a reservation
    @Test
    public void testGetCustomerID() {

        assertEquals(1, testReservation.getCustomerID());
    }

    // Verifies the timestamp of a reservation
    @Test
    public void testGetTimestamp() {

        assertSame(LocalDateTime.of(2026, 2, 1, 12, 0), testReservation.getTimeStamp());
    }

    // Verifies the claim code of a reservation
    @Test
    public void testGetClaimCode() {

        assertSame("PLACEHOLDER", testReservation.getClaimCode());
    }

    // verifies the status of a reservation
    @Test
    public void testGetStatus() {

        assertSame("Claimed", testReservation.getStatus());
    }

    // Verifies the weather flag of a reservation
    @Test
    public void testGetWeatherFlag() {

        assertSame("Raining", testReservation.getWeatherFlag());
    }

    /////////////////////////////////////////////////////////


    // Verifies changing the ID of a reservation
    @Test
    public void testSetReservationID() {

        testReservation.setReservationID(3);
        assertEquals(3, testReservation.getReservationID());
    }

    // Verifies changing the posting ID of a reservation
    @Test
    public void testSetPostingID() {

        testReservation.setPostingID(3);
        assertEquals(3, testReservation.getPostingID());
    }

    // Verifies changing the customer ID of a reservation
    @Test
    public void testSetCustomerID() {

        testReservation.setCustomerID(3);
        assertEquals(3, testReservation.getCustomerID());
    }

    // Verifies changing the timestamp of a reservation
    @Test
    public void testSetTimestamp() {

        testReservation.setTimeStamp(LocalDateTime.now());
        assertSame(LocalDateTime.now(), testReservation.getTimeStamp());
    }

    // Verifies changing the claim code of a reservation
    @Test
    public void testSetClaimCode() {

        testReservation.setClaimCode("ANOTHER_PLACEHOLDER");
        assertSame("ANOTHER_PLACEHOLDER", testReservation.getClaimCode());
    }

    // Verifies changing the status of a reservation
    @Test
    public void testSetStatus() {

        testReservation.setStatus("Unclaimed");
        assertSame("Unclaimed", testReservation.getStatus());
    }

    // Verifies changing the weather flag of a reservation
    @Test
    public void testSetWeatherFlag() {

        testReservation.setWeatherFlag("Sunny");
        assertSame("Sunny", testReservation.getWeatherFlag());
    }
}
