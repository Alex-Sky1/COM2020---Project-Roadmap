package com.waste_manager.team_roadmap.tests;

import com.waste_manager.team_roadmap.Customer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class CustomerTest {

    static Customer testCustomer;

    // Ran once before any of the tests
    @BeforeEach
    public void setup() {
        testCustomer = new Customer("jim", "bob", "Jimmy", "no man's land",
                "NW1 6XE", "test", "test@gmail.com", "0000008776", "jim"
                , 6, new ArrayList<>(List.of(false, false, false, false, false)), true);

        testCustomer.setCustomerID(1L);
    }

    @Test
    public void TestGenerateClaimCode() {
        String characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYS0123456789";

        for (int i = 0; i < 10; i++) {
            String hold = testCustomer.generateClaimCode();

            assertEquals(6, hold.length());
            for (int j = 0; j < 6; j++) {
                char hold2 = hold.charAt(j);
                assertTrue(characters.indexOf(hold2) != -1);
            }
        }
    }

    @Test
    public void testGetCustomerID() {
        assertEquals(1L, testCustomer.getCustomerID());
    }

    @Test
    public void testSetCustomerID() {
        testCustomer.setCustomerID(5L);
        assertEquals(5L, testCustomer.getCustomerID());
    }

    @Test
    public void testGetdName() {
        assertEquals("Jimmy", testCustomer.getdName());
    }

    @Test
    public void testSetdName() {
        testCustomer.setdName("Mr");
        assertEquals("Mr", testCustomer.getdName());
    }

    @Test
    public void testGetfName() {
        assertEquals("jim", testCustomer.getfName());
    }

    @Test
    public void testSetfName() {
        testCustomer.setfName("John");
        assertEquals("John", testCustomer.getfName());
    }

    @Test
    public void testGetsName() {
        assertEquals("bob", testCustomer.getsName());
    }

    @Test
    public void testSetsName() {
        testCustomer.setsName("Smith");
        assertEquals("Smith", testCustomer.getsName());
    }

    @Test
    public void testGetAddress() {
        assertEquals("no man's land", testCustomer.getAddress());
    }

    @Test
    public void testSetAddress() {
        testCustomer.setAddress("221B Baker Street");
        assertEquals("221B Baker Street", testCustomer.getAddress());
    }

    @Test
    public void testGetPostcode() {
        assertEquals("NW1 6XE", testCustomer.getPostcode());
    }

    @Test
    public void testSetPostcode() {
        testCustomer.setPostcode("NW1 6XL");
        assertEquals("NW1 6XL", testCustomer.getPostcode());
    }

    @Test
    public void testGetCounty() {
        assertEquals("test", testCustomer.getCounty());
    }

    @Test
    public void testSetCounty() {
        testCustomer.setCounty("Greater London");
        assertEquals("Greater London", testCustomer.getCounty());
    }

    @Test
    public void testGetEmail() {
        assertEquals("test@gmail.com", testCustomer.getEmail());
    }

    @Test
    public void testSetEmail() {
        testCustomer.setEmail("john.smith@email.com");
        assertEquals("john.smith@email.com", testCustomer.getEmail());
    }

    @Test
    public void testGetPhone() {
        assertEquals("0000008776", testCustomer.getPhone());
    }

    @Test
    public void testSetPhone() {
        testCustomer.setPhone("07123 456789");
        assertEquals("07123 456789", testCustomer.getPhone());
    }

    @Test
    public void testGetStreak() {
        assertEquals(6, testCustomer.getStreak());
    }

    @Test
    public void testSetStreak() {
        testCustomer.setStreak(10);
        assertEquals(10, testCustomer.getStreak());
    }

    @Test
    public void testGetBadges() {
        ArrayList<Boolean> badges = new ArrayList<>(List.of(false, false, false, false, false));
        assertEquals(badges, testCustomer.getBadges());
    }

    @Test
    public void testSetBadges() {
        ArrayList<Boolean> badges = new ArrayList<>();
        badges.add(true);
        badges.add(false);
        testCustomer.setBadges(badges);
        assertEquals(badges, testCustomer.getBadges());
    }

    @Test
    public void testGetPassword() {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        assertTrue(passwordEncoder.matches("jim", testCustomer.getPassword()));
    }

    @Test
    public void testSetPassword() {
        testCustomer.setPassword("Str0ngPass!");
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        assertTrue(passwordEncoder.matches("Str0ngPass!", testCustomer.getPassword()));
    }
}