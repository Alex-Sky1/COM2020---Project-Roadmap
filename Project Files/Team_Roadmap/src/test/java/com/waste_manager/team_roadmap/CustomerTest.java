package com.waste_manager.team_roadmap;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class CustomerTest {

    static Customer testCustomer;
    // Ran once before any of the tests
    @BeforeEach
    public void setup(){
        testCustomer = new Customer("jim", "bob", "Jimmy", "no man's land",
                "test", "test", "test@gmail.com", "0000008776", "jim"
                , 6, new ArrayList<>(List.of(false, false, false, false, false)));
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

        assertSame(new ArrayList<>(List.of(false, false, false, false, false)), testCustomer.getBadges());
    }


    /// //////////////////////////////////////////////////////////////


    // Verifies changing the ID of a customer
    @Test
    public void testSetCustomerID() {

        testCustomer.setCustomerID((long) 3);
        assertEquals(3, testCustomer.getCustomerID());
    }

    // Verifies changing the display name of a customer
    @Test
    public void testSetDisplayName() {

        testCustomer.setdName("timmy");
        assertSame("timmy", testCustomer.getdName());
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

        ArrayList<Boolean> contents = new ArrayList<>(List.of(true, false, false, false, false));
        testCustomer.setBadges(contents);
        assertEquals(new ArrayList<>(List.of(true, false, false, false, false)), testCustomer.getBadges());
    }
}
