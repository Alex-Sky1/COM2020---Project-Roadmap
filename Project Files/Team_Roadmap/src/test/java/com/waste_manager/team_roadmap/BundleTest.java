package com.waste_manager.team_roadmap;



import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


public class BundleTest {

    static Bundle testBundle;

    // Ran once before any of the tests
    @BeforeAll
    public static void setup() {
        Bundle testBundle = new Bundle(); // Constructor doesn't exist currently
    }

    // Verifies the ID of a bundle
    @Test
    public void testGetPostID() {

        assertEquals(1, testBundle.getPostingID());
    }

    // Verifies the ID of the bundle seller
    @Test
    public void testGetSellerID() {

        assertEquals(1, testBundle.getSellerID());
    }

    // Verifies the category of the bundle
    @Test
    public void testGetCategory() {

        assertSame("PLACEHOLDER", testBundle.getCategory()); // Need to check what categories exist
    }

    @Test
    public void testGetContents() {

        assertArrayEquals(new String[]{"brownie", "chocolate", "sugar"}, testBundle.getContents());
    }

//    @Test
//    public void testGetItem() { // Need to see how the method will work
//
//        testBundle.addItem("Cheese");
//        assertTrue(testBundle.getItem("Cheese"));
//    }

    @Test
    public void testGetAllergens() {

        assertArrayEquals(new String[]{"gluten", "shellfish"}, testBundle.getAllergens());
    }

    @Test
    public void testGetQuantity() {

        assertEquals(1, testBundle.getQuantity());
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

        testBundle.setSellerID(3);
        assertEquals(3, testBundle.getSellerID());
    }

    // Verifies the category of the bundle
    @Test
    public void testSetCategory() {

        testBundle.setCategory("Desserts");
        assertSame("Desserts", testBundle.getCategory());
    }

    @Test
    public void testSetContents() {

        String[] contents = {"dairy", "fish"};
        testBundle.setContents(contents);
        assertArrayEquals(new String[]{"dairy", "fish"}, testBundle.getContents());
    }

    @Test
    public void testSetAllergens() {

        String[] allergens = {"gluten", "dairy", "peanuts"};
        testBundle.setContents(allergens);
        assertArrayEquals(new String[]{"gluten", "dairy", "peanuts"}, testBundle.getAllergens());
    }

    @Test
    public void testSetQuantity() {

        testBundle.setQuantity(3);
        assertEquals(3, testBundle.getQuantity());
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
