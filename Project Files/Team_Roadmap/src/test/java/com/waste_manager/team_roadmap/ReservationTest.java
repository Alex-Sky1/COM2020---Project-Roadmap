package com.waste_manager.team_roadmap;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class ReservationTest {

    // Initialising the test instance to allow unit tests to be run
    static Reservation testReservation;
    static Customer testCustomer;
    static Seller testSeller;
    static Bundle testBundle;

    // Ran once before any of the tests
    @BeforeAll
    public static void setup() {
        LocalDateTime testTime = LocalDateTime.of(2026, 2, 1, 12, 0);

        testSeller = new Seller("Peter", "Pan", "Pete's Pancakes", "Neverland", "NV21 TK2", "Crocodile Creek",
                "Peter.Pan12@hookmail.com", "06847 268425", "T1nkerb3ll!");
        testCustomer = new Customer("Robin", "Hood", "RobOfNotts1364", "Sherwood Forest", "NG21 9RM", "Nottinghamshire",
                "rHood1334@ping,com", "07862 561843", "L1tt!e John", 1, new ArrayList<>(List.of(false, false, false, false, false)));
        testBundle = new Bundle(testSeller, "puddings", new ArrayList<>(List.of("chocolate", "pancakes", "brownies", "cake")),
                new ArrayList<>(List.of("gluten", "dairy")), testTime, 7.50f, 10, 1700,
                true, false);
        testReservation = new Reservation(testBundle, testCustomer, testSeller, testTime, "AAAAAA",
                false, true, "rainy");
    }

//    // Verifies the ID of a reservation
//    @Test
//    public void testGetReservationID() {assertEquals(1, testReservation.getReservationID());}
//
//    // Verifies changing the ID of a reservation
//    @Test
//    public void testSetReservationID() {
//
//        testReservation.setReservationID(3);
//        assertEquals(3, testReservation.getReservationID());
//    }

    // Verifies the Posting ID of a reservation
    @Test
    public void testGetPostingID() {assertEquals(1, testReservation.getReservationID());}

    // Verifies changing the posting ID of a reservation
    @Test
    public void testSetPostingID() {

        testReservation.setReservationID(3);
        assertEquals(3, testReservation.getReservationID());
    }

    // Verifies the customer of a reservation
    @Test
    public void testGetCustomer() {assertEquals(testCustomer, testReservation.getCustomer());}

    // Verifies changing the customer of a reservation
    @Test
    public void testSetCustomer() {

        Customer newCustomer = new Customer("Reanne", "Hood", "RobOfNotts1364", "Sherwood Forest", "NG21 9RM", "Nottinghamshire",
                "rHood1334@ping,com", "07862 561843", "L1tt!e John", 1, new ArrayList<>(List.of(false, false, false, false, false)));
        testReservation.setCustomer(newCustomer);
        assertEquals(newCustomer, testReservation.getCustomer());
    }

    // Verifies the timestamp of a reservation
    @Test
    public void testGetTimestamp() {assertSame(LocalDateTime.of(2026, 2, 1, 12, 0), testReservation.getTimeStamp());}

    // Verifies changing the timestamp of a reservation
    @Test
    public void testSetTimestamp() {

        LocalDateTime newTime = LocalDateTime.now();
        testReservation.setTimeStamp(newTime);
        assertSame(newTime, testReservation.getTimeStamp());
    }

    // Verifies the claim code of a reservation
    @Test
    public void testGetClaimCode() {assertSame("AAAAAA", testReservation.getClaimCode());}

    // Verifies changing the claim code of a reservation
    @Test
    public void testSetClaimCode() {

        testReservation.setClaimCode("BBBBBB");
        assertSame("BBBBBB", testReservation.getClaimCode());
    }

    // verifies the status of a reservation
    @Test
    public void testGetNoShow() {assertSame(false, testReservation.getNoShow());}

    // Verifies changing the status of a reservation
    @Test
    public void testSetNoShow() {

        testReservation.setNoShow(true);
        assertSame(true, testReservation.getNoShow());
    }

    @Test
    public void testGetCollected() {assertSame(false, testReservation.getCollected());}

    // Verifies changing the status of a reservation
    @Test
    public void testSetCollected() {

        testReservation.setCollected(true);
        assertSame(true, testReservation.getCollected());
    }

    // Verifies the weather flag of a reservation
    @Test
    public void testGetWeatherFlag() {assertSame("rainy", testReservation.getWeatherFlag());}

    // Verifies changing the weather flag of a reservation
    @Test
    public void testSetWeatherFlag() {

        testReservation.setWeatherFlag("Sunny");
        assertSame("Sunny", testReservation.getWeatherFlag());
    }
}
