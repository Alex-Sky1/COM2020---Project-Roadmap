package com.waste_manager.team_roadmap;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;


public class BundleTest {

    static Bundle testBundle;
    static Seller testSeller;

    // Ran once before any of the tests
    @BeforeAll
    public static void setup() {

        testSeller = new Seller("Peter", "Pan", "Pete's Pancakes", "Neverland", "NV21 TK2", "Crocodile Creek",
                            "Peter.Pan12@hookmail.com", "06847 268425", "T1nkerb3ll!");

        testBundle = new Bundle(testSeller, "Deserts",new ArrayList<>(List.of("pancakes", "cake", "brownies")),
                                new ArrayList<>(List.of("gluten", "dairy", "peanuts")), LocalDateTime.now(), 11.50f, 10,
                1230, false, false); 
    }


    @Test
    public void testGetAllergensAsString(){
        assertEquals("gluten,dairy,peanuts",testBundle.getAllergensAsString());
    }

    @Test
    public void testGetContentsAsString(){
        assertEquals("pancakes,cake,brownies",testBundle.getContentsAsString());
    }

    // Verifies the ID of a bundle
    @Test
    public void testGetPostID() {

        assertEquals(1, testBundle.getPostingID());
    }

     //Verifies the ID of the bundle seller

     //Needs changing to a Seller object
    @Test
    public void testGetSellerID() {

        assertEquals(testSeller, testBundle.getSeller());
    }

    // Verifies the category of the bundle
    @Test
    public void testGetCategory() {

        assertSame("PLACEHOLDER", testBundle.getCategory()); // Need to check what categories exist
    }

    @Test
    public void testGetContents() {

        assertEquals(new ArrayList<>(List.of("Cake, Brownies, Doughnut")), testBundle.getContents());
    }

    @Test
    public void testGetAllergens() {

        assertEquals(new ArrayList<>(List.of("gluten, dairy, peanuts")), testBundle.getAllergens());
    }

    @Test
    public void testGetPrice() {

        assertEquals(10.00, testBundle.getPrice());
    }

    @Test
    public void testGetDiscount() {

        assertEquals(10, testBundle.getDiscount());
    }

    @Test
    public void testGetPickUpWindow() {

        assertEquals(1, testBundle.getPickUpWindow());
    }

    @Test
    public void testGetReserved() {

        assertTrue(testBundle.getReserved());
    }

    @Test
    public void testGetExpired() {

        assertTrue(testBundle.getExpired());
    }


    /////////////////////////////////////////////////////////////////

    // Verifies the ID of a bundle
    @Test
    public void testSetPostID() {

        testBundle.setPostingID(3);
        assertEquals(3, testBundle.getPostingID());
    }

    // Verifies the ID of the bundle seller
    @Test
    public void testSetSellerID() {

        Seller newTestSeller = new Seller("Peter", "Pan", "Pete's Pancakes", "Neverland", "NV21 TK2", "Crocodile Creek",
                "Peter.Pan12@hookmail.com", "06847 268425", "T1nkerb3ll!");
        testBundle.setSeller(newTestSeller);
        assertEquals(newTestSeller, testBundle.getSeller());
    }

    // Verifies the category of the bundle
    @Test
    public void testSetCategory() {

        testBundle.setCategory("Desserts");
        assertSame("Desserts", testBundle.getCategory());
    }

    @Test
    public void testSetContents() {

        ArrayList<String> contents = new ArrayList<>(List.of("dairy", "fish"));
        testBundle.setContents(contents);
        assertEquals(contents, testBundle.getContents());
    }

    @Test
    public void testSetAllergens() {

        ArrayList<String> allergens = new ArrayList<>(List.of("gluten, dairy, peanuts"));
        testBundle.setAllergens(allergens);
        assertEquals(allergens, testBundle.getAllergens());
    }

    @Test
    public void testSetPrice() {

        testBundle.setPrice(9.00F);
        assertEquals(9.00, testBundle.getPrice());
    }

    @Test
    public void testSetDiscount() {

        testBundle.setDiscount(9);
        assertEquals(9, testBundle.getDiscount());
    }

    @Test
    public void testSetPickUpWindow() {

        testBundle.setPickUpWindow(9);
        assertEquals(9, testBundle.getPickUpWindow());
    }

    @Test
    public void testSetReserved() {

        testBundle.setReserved(false);
        assertFalse(testBundle.getReserved());
    }

    @Test
    public void testSetExpired() {

        testBundle.setExpired(false);
        assertFalse(testBundle.getExpired());
    }
}
