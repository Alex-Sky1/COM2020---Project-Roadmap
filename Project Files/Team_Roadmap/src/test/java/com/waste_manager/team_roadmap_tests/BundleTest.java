package com.waste_manager.team_roadmap_tests;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import com.waste_manager.team_roadmap.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class BundleTest {

    static Bundle testBundle;
    static Seller testSeller;
    static LocalDateTime testTime = LocalDateTime.now();

    // Ran once before any of the tests
    @BeforeEach
    public void setup() {

        testSeller = new Seller("Peter", "Pan", "Pete's Pancakes", "Neverland", "NV21 TK2", "Crocodile Creek",
                            "Peter.Pan12@hookmail.com", "06847 268425", "T1nkerb3ll!", true);

        testBundle = new Bundle(testSeller, "Deserts",new ArrayList<>(List.of("pancakes", "cake", "brownies")),
                                new ArrayList<>(List.of("gluten", "dairy", "peanuts")), testTime, 11.50f, 10,
                12, false, false);
    }

    @Test
    public void testGetPostID() {assertEquals(0, testBundle.getPostingID());}

    @Test
    public void testSetPostID() {
        testBundle.setPostingID(3);
        assertEquals(3, testBundle.getPostingID());
    }


    @Test
    public void testGetSeller() {assertEquals(testSeller, testBundle.getSeller());}

    @Test
    public void testSetSeller() {
        Seller newTestSeller = new Seller("Peter", "Pan", "Pete's Pancakes", "Neverland", "NV21 TK2", "Crocodile Creek",
                "Peter.Pan12@hookmail.com", "06847 268425", "T1nkerb3ll!", true);
        testBundle.setSeller(newTestSeller);
        assertEquals(newTestSeller, testBundle.getSeller());
    }


    @Test
    public void testGetCategory() {assertSame("Deserts", testBundle.getCategory());}

    @Test
    public void testSetCategory() {
        testBundle.setCategory("Desserts");
        assertSame("Desserts", testBundle.getCategory());
    }


    @Test
    public void testGetContents() {
        assertEquals(new ArrayList<>(List.of("pancakes", "cake", "brownies")), testBundle.getContents());
    }

    @Test
    public void testSetContents() {
        ArrayList<String> contents = new ArrayList<>(List.of("dairy", "fish"));
        testBundle.setContents(contents);
        assertEquals(contents, testBundle.getContents());
    }


    @Test
    public void testGetContentsAsString() {
        assertEquals("pancakes,cake,brownies", testBundle.getContentsAsString());
    }


    @Test
    public void testGetAllergens() {
        assertEquals(new ArrayList<>(List.of("gluten", "dairy", "peanuts")), testBundle.getAllergens());
    }

    @Test
    public void testSetAllergens() {
        ArrayList<String> allergens = new ArrayList<>(List.of("gluten, dairy, peanuts"));
        testBundle.setAllergens(allergens);
        assertEquals(allergens, testBundle.getAllergens());
    }


    @Test
    public void testGetAllergensAsString() {
        assertEquals("gluten,dairy,peanuts", testBundle.getAllergensAsString());
    }


    @Test
    public void testGetPrice() {assertEquals(11.50, testBundle.getPrice());}

    @Test
    public void testSetPrice() {
        testBundle.setPrice(9.00F);
        assertEquals(9.00, testBundle.getPrice());
    }


    @Test
    public void testGetTimeStamp() {assertEquals(testTime, testBundle.getTimeStamp());}

    @Test
    public void testSetTimestamp() {
        testBundle.setTimeStamp(testTime.plusDays(1));
        assertEquals(testTime.plusDays(1), testBundle.getTimeStamp());
    }


    @Test
    public void testGetDiscount() {assertEquals(10, testBundle.getDiscount());}

    @Test
    public void testSetDiscount() {
        testBundle.setDiscount(9);
        assertEquals(9, testBundle.getDiscount());
    }


    @Test
    public void testGetPickUpWindow() {assertEquals(12, testBundle.getPickUpWindow());}

    @Test
    public void testSetPickUpWindow() {
        testBundle.setPickUpWindow(9);
        assertEquals(9, testBundle.getPickUpWindow());
    }


    @Test
    public void testGetReserved() {assertFalse(testBundle.getReserved());}

    @Test
    public void testSetReserved() {
        testBundle.setReserved(false);
        assertFalse(testBundle.getReserved());
    }


    @Test
    public void testGetExpired() {assertFalse(testBundle.getExpired());}

    @Test
    public void testSetExpired() {
        testBundle.setExpired(false);
        assertFalse(testBundle.getExpired());
    }
}
