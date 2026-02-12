package com.waste_manager.team_roadmap;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CustomerTest {

    static Customer testCustomer;
    // Ran once before any of the tests
    @BeforeAll
    public static void setup(){
        testCustomer = new Customer("jim", "bob", "Jimmy", "no man's land", "test", "test", "test@gmail.com", "0000008776", "jim", 6, ArrayList<Boolean> badges); // Constructor doesn't exist currently
    }


    @Test
    public void TestGenerateClaimCode(){
        String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYS0123456789";

        for(int i =0; i < 10; i++) {
            String hold = testCustomer.generateClaimCode();

            assertEquals(6,hold.length());
            for(int j = 0; j< 6; j++){
                char hold2 = hold.charAt(j);
                assertTrue(characters.indexOf(hold2) != -1);
            }
        }



    }



    // Verifies the ID of a customer
    @Test
    public void testGetCustomerID() {

        assertEquals(1, testCustomer.getCustomerID());
    }

    //Verifies the display name of a customer
    @Test
    public void testGetDisplayName() {

        assertSame("Jimmy", testCustomer.getdName());
    }

    //Verifies the streak of a customer
    @Test
    public void testGetStreak() {

        assertEquals(6, testCustomer.getStreak());
    }

    //Verifies the badges of a customer
    @Test
    public void testGetBadges() {

        assertSame(new boolean[]{true, true, true}, testCustomer.getBadges());
    }


    /// //////////////////////////////////////////////////////////////


    // Verifies changing the ID of a customer
    @Test
    public void testSetCustomerID() {

        testCustomer.setCustomerID(3);
        assertEquals(3, testCustomer.getCustomerID());
    }

    // Verifies changing the display name of a customer
    @Test
    public void testSetDisplayName() {

        testCustomer.setDisplayName("timmy");
        assertSame("timmy", testCustomer.getDisplayName());
    }

    // Verifies changing the streak of a customer
    @Test
    public void testSetStreak() {

        testCustomer.setStreak(7);
        assertEquals(7, testCustomer.getStreak());
    }

    // Verifies changing the badges of a customer
    @Test
    public void testSetBadges() {

        boolean[] contents = {false, true, true};
        testCustomer.setBadges(contents);
        assertArrayEquals(new boolean[]{false, true, true}, testCustomer.getBadges());


    }
}
