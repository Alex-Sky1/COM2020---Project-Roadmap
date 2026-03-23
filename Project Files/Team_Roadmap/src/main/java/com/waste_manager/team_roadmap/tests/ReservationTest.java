package com.waste_manager.team_roadmap.tests;

import com.waste_manager.team_roadmap.Bundle;
import com.waste_manager.team_roadmap.Customer;
import com.waste_manager.team_roadmap.Reservation;
import com.waste_manager.team_roadmap.Seller;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

public class ReservationTest {

    // Initialising the test instance to allow unit tests to be run
    static Reservation testReservation;
    static Customer testCustomer;
    static Seller testSeller;
    static Bundle testBundle;
    static LocalDateTime testTime;

    // Ran once before any of the tests
    @BeforeEach
    public void setup() {
        testTime = LocalDateTime.of(2026, 2, 1, 12, 0);

        testSeller = new Seller("Peter", "Pan", "Pete's Pancakes", "Neverland", "NV21 TK2", "Crocodile Creek",
                "Peter.Pan12@hookmail.com", "06847 268425", "T1nkerb3ll!", true);
        testCustomer = new Customer("Robin", "Hood", "RobOfNotts1364", "Sherwood Forest", "NG21 9RM", "Nottinghamshire",
                "rHood1334@ping,com", "07862 561843", "L1tt!e John", 1, new ArrayList<>(List.of(false, false, false, false, false)), true);
        testBundle = new Bundle(testSeller, "puddings", new ArrayList<>(List.of("chocolate", "pancakes", "brownies", "cake")),
                new ArrayList<>(List.of("gluten", "dairy")), testTime, 7.50f, 10, 1700,
                true, false, "overcast");
        testReservation = new Reservation(testBundle, testCustomer, testSeller, testTime, "AAAAAA",
                false, false);

        testReservation.setPickupTimeStamp(testTime);
    }

    // Verifies the Posting ID of a reservation
    @Test
    public void testGetPostingID() {assertEquals(0, testReservation.getReservationID());}

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
                "rHood1334@ping,com", "07862 561843", "L1tt!e John", 1, new ArrayList<>(List.of(false, false, false, false, false)), true);
        testReservation.setCustomer(newCustomer);
        assertEquals(newCustomer, testReservation.getCustomer());
    }

    // Verifies the timestamp of a reservation
    @Test
    public void testGetTimestamp() {assertEquals(LocalDateTime.of(2026, 2, 1, 12, 0), testReservation.getTimeStamp());}

    // Verifies changing the timestamp of a reservation
    @Test
    public void testSetTimestamp() {

        LocalDateTime newTime = LocalDateTime.now();
        testReservation.setTimeStamp(newTime);
        assertSame(newTime, testReservation.getTimeStamp());
    }

    @Test
    public void testGetPickupTimeStamp() {assertEquals(testTime, testReservation.getPickupTimeStamp());}

    @Test
    public void getGetPickupTimeStamp() {

        testReservation.setPickupTimeStamp(testTime.plusDays(1));
        assertEquals(testTime.plusDays(1), testReservation.getPickupTimeStamp());
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
}
