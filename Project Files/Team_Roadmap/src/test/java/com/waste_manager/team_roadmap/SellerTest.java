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
        testSeller = new Seller("Peter", "Pan", "Pete's Pancakes", "Neverland", "NV21 TK2", "Crocodile Creek",
                "Peter.Pan12@hookmail.com", "06847 268425", "T1nkerb3ll!");
    }

    // Verifies the ID of a bundle
    @Test
    public void testGetSellerID() {assertEquals(1, testSeller.getSellerID());}

    @Test
    public void testSetSellerID() {

        testSeller.setSellerID(3);
        assertEquals(3, testSeller.getSellerID());
    }

    @Test
    public void testGetFName() {assertSame("Peter", testSeller.getfName());}

    @Test
    public void testSetFName() {

        testSeller.setfName("Stephen");
        assertSame("Stephen", testSeller.getfName());
    }

    @Test
    public void testGetSName() {assertSame("Pan", testSeller.getsName());}

    @Test
    public void testSetSName() {

        testSeller.setsName("Fry");
        assertSame("Fry", testSeller.getsName());
    }

    @Test
    public void testDName() {assertEquals("Pete's Pancakes", testSeller.getdName());}

    @Test
    public void testSetDName() {

        testSeller.setdName("Stephen's Shakes");
        assertSame("Stephen's Shakes", testSeller.getdName());
    }

    @Test
    public void testGetAddress() {assertEquals("Neverland", testSeller.getAddress());}

    @Test
    public void testSetAddress() {
        testSeller.setAddress("UK");
        assertEquals("UK", testSeller.getAddress());
    }

    @Test
    public void testGetPostcode() {assertEquals("NV21 TK2", testSeller.getPostcode());}

    @Test
    public void testSetPostcode() {
        testSeller.setPostcode("SE7 4KJ");
        assertEquals("SE7 4KJ", testSeller.getPostcode());
    }

    @Test
    public void testGetEmail() {assertEquals("Peter.Pan12@hookmail.com", testSeller.getEmail());}

    @Test
    public void testSetEmail() {
        testSeller.setEmail("Stephen.Fry12@hookmail.com");
        assertEquals("Stephen.Fry@hookmail.com", testSeller.getEmail());
    }

    @Test
    public void testGetPhone() {assertEquals("06847 268425", testSeller.getPhone());}

    @Test
    public void testSetPhone() {
        testSeller.setPhone("0958 784525");
        assertEquals("0958 784525", testSeller.getPhone());
    }

    @Test
    public void testGetPassword() {assertEquals("T1nkerb3ll!", testSeller.getPassword());}

    @Test
    public void testSetPassword() {
        testSeller.setPassword("HughLaurie2!");
        assertEquals("HughLaurie2!", testSeller.getPassword());
    }
}
