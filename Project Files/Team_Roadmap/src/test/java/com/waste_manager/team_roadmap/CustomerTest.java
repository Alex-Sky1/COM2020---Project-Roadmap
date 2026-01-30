package com.waste_manager.team_roadmap;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CustomerTest {

    // Ran once before any of the tests
    @BeforeAll
    public static void setup() throws IllegalPackSizeException {
        Customer testCustomer = new Customer(); // Constructor doesn't exist currently
    }

    // Verifies the ID of a bundle
    @Test
    public void testGetCustomerID() {

        assertTrue(testCustomer.getCustomerID() == 1);
    }

    @Test
    public void testGetDisplayName() {

        assertSame("Jimmy", testCustomer.getDisplayName());
    }

    @Test
    public void testGetStreak() {

        assertTrue(testCustomer.getStreak() == 6);
    }

    @Test
    public void testGetBadges() {

        assertSame("PLACEHOLDER", testCustomer.getBadges());
    }


    /// //////////////////////////////////////////////////////////////


    @Test
    public void testSetCustomerID() {

        testBundle.setCustomerID(3);
        assertTrue(testBundle.getCustomerID() == 3);
    }

    // Verifies the ID of the bundle seller
    @Test
    public void testSetDisplayName() {

        testBundle.setDisplayName("timmy");
        assertSame("timmy", testBundle.getDisplayName());
    }

    // Verifies the category of the bundle
    @Test
    public void testSetStreak() {

        testBundle.setStreak(7);
        assertTrue(testBundle.getStreak() == 7);
    }

    @Test
    public void testSetBadges() {

        boolean[] contents = {false, true, true};
        testBundle.setBadges(contents);
        assertTrue(testBundle.getBadges.equals(new boolean[]{false, true, true}));


    }
}
