package com.waste_manager.team_roadmap;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SellerTest {

    static Seller testSeller;

    // Ran once before any of the tests
    @BeforeAll
    public static void setup(){
        testSeller = new Seller(1, "Pete's Puddings", new ArrayList<>(List.of(254, 89)),
                                new ArrayList<>(List.of(9, 18)), "PPU");
    }

    // Verifies the ID of a bundle
    @Test
    public void testGetSellerID() {

        assertEquals(1, testSeller.getSellerID());
    }

    @Test
    public void testGetName() {

        assertSame("Steven", testSeller.getName());
    }

    @Test
    public void testGetLocation() {

        assertEquals(new ArrayList<>(List.of(254, 897)), testSeller.getLocation());
    }

    @Test
    public void testGetOpeningHours() {

        assertEquals(new ArrayList<>(List.of(9, 18)), testSeller.getOpeningHours());
    }

    @Test
    public void testGetContactStub() {

        assertEquals("PPU", testSeller.getContactStub());
    }

    //////

    @Test
    public void testSetSellerID() {

        testSeller.setSellerID(3);
        assertEquals(3, testSeller.getSellerID());
    }

    @Test
    public void testSetName() {

        testSeller.setName("Stephen");
        assertSame("Stephen", testSeller.getName());
    }

    @Test
    public void testSetLocation() {

        testSeller.setLocation(new ArrayList<>(List.of(45, 98)));
        assertEquals(new ArrayList<>(List.of(45, 98)), testSeller.getLocation());
    }

    @Test
    public void testSetOpeningHours() {

        testSeller.setOpeningHours(new ArrayList<>(List.of(7, 20)));
        assertEquals(new ArrayList<>(List.of(7, 20)), testSeller.getOpeningHours());
    }

    @Test
    public void testSetContactStub() {

        testSeller.setContactStub("PLACEHOLDER");
        assertSame("PLACEHOLDER", testSeller.getContactStub()); // What is a contact stub?
    }
}
