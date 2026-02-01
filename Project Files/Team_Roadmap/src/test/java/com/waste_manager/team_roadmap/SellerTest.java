package com.waste_manager.team_roadmap;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class SellerTest {

    static Seller testSeller;

    // Ran once before any of the tests
    @BeforeAll
    public static void setup(){
        testSeller = new Seller(); // Constructor doesn't exist currently
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

        assertArrayEquals(new int[]{43, 65}, testSeller.getLocation());
    }

    @Test
    public void testGetOpeningHours() {

        assertEquals(new int[]{8, 19}, testSeller.getOpeningHours());
    }

    @Test
    public void testGetContactStub() {

        // assertArrayEquals(new String[]{"brownie", "chocolate", "sugar"}, testBundle.getContents());
        assertEquals("PLACEHOLDER", testSeller.getContactStub()); // What is a contact stub?
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

        int[] newLocation = new int[]{45, 98};
        testSeller.setLocation(newLocation);
        assertEquals(new int[]{45, 98}, testSeller.getLocation());
    }

    @Test
    public void testSetOpeningHours() {

        testSeller.setOpeningHours(new int[]{7, 20});
        assertEquals(new int[]{7, 20}, testSeller.getOpeningHours());
    }

    @Test
    public void testSetContactStub() {

        testSeller.setContactStub("PLACEHOLDER");
        assertSame("PLACEHOLDER", testSeller.getContactStub()); // What is a contact stub?
    }
}
