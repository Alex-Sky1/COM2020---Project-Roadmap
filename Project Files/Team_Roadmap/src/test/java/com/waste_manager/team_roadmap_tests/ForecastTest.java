package com.waste_manager.team_roadmap_tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import com.waste_manager.team_roadmap.*;

import static org.junit.jupiter.api.Assertions.*;

public class ForecastTest {

    static Forecast testForecast;
    static LocalDateTime testTime;
    static Seller testSeller;
    static Customer testCustomer;
    static ArrayList<String> testItems;
    static ArrayList<String> testAllergies;
    static ArrayList<Bundle> testBundles;
    static ArrayList<Reservation> testReservations;


    // Ran once before any of the tests
    @BeforeEach
    public void setup() {

        testTime = LocalDateTime.of(2026, 2, 1, 12, 0);
        testBundles = new ArrayList<>();
        testReservations = new ArrayList<>();

        testSeller = new Seller("Peter", "Pan", "Pete's Pancakes", "Neverland", "NV21 TK2", "Crocodile Creek",
                "Peter.Pan12@hookmail.com", "06847 268425", "T1nkerb3ll!", true);
        testCustomer = new Customer("Robin", "Hood", "RobOfNotts1364", "Sherwood Forest", "NG21 9RM", "Nottinghamshire",
                "rHood1334@ping,com", "07862 561843", "L1tt!e John", 1, new ArrayList<>(List.of(false, false, false, false, false)), true);

        testItems = new ArrayList<>(List.of("chocolate", "pancakes", "brownies", "cake", "biscuit", "pavlova", "toffee", "ice cream"));
        testAllergies = new ArrayList<>(List.of("dairy", "gluten", "nuts", "fish", "eggs"));

        for (int i = 0; i < 4; i++) {

            Collections.shuffle(testItems); Collections.shuffle(testAllergies);
            Bundle testBundle = new Bundle(testSeller, "puddings", testItems.stream().limit(5).collect(Collectors.toCollection(ArrayList::new)),
                                            testAllergies.stream().limit(5).collect(Collectors.toCollection(ArrayList::new)), testTime, 7.50f, 10, 12,
                                    true, false);
            testBundles.add(testBundle);

            Reservation testReservation = new Reservation(testBundle, testCustomer, testSeller, testTime, "AAAAAA",
                    false, true, "rainy");
            testReservations.add(testReservation);
        }

        testForecast = new Forecast(testTime.plusDays(7), 0, "rainy", "puddings", testBundles, testReservations);
        testForecast.setConfidence(0.5f);
        testForecast.setRationale("Good food");

    }

    @Test
    public void testBundleFromSelectSeller(){
        ArrayList<Bundle> filteredBundleList = testForecast.bundleFromSelectSeller();
        assertEquals(testBundles, filteredBundleList);
    }

    @Test
    public void testSeasonalNaive() {
        int seasonalNaiveTestReturn = testForecast.seasonalNaive();
        assertEquals(4, seasonalNaiveTestReturn);
    }

    @Test
    public void testGetForecastDate() {assertEquals(testTime.plusDays(7), testForecast.getForecastDate());}

    @Test
    public void testSetForecastDate() {

        LocalDateTime newTime = LocalDateTime.now();
        testForecast.setForecastDate(newTime);
        assertEquals(newTime, testForecast.getForecastDate());
    }

    @Test
    public void testGetSellerID() {assertEquals(0, testForecast.getSellerID());}

    @Test
    public void testSetSellerID() {

        testForecast.setSellerID(10);
        assertEquals(10, testForecast.getSellerID());
    }

    @Test
    public void testGetCategory() {assertSame("puddings", testForecast.getCategory());}

    @Test
    public void testSetCategory() {

        testForecast.setCategory("deserts");
        assertSame("deserts", testForecast.getCategory());
    }

    @Test
    public void testGetWeatherFlag() {assertSame("rainy", testForecast.getWeatherFlag());}

    @Test
    public void testSetWeatherFlag() {

        testForecast.setWeatherFlag("sunny");
        assertSame("sunny", testForecast.getWeatherFlag());
    }

    @Test
    public void testGetConfidence() {assertEquals(0.5F, testForecast.getConfidence());}

    @Test
    public void testSetConfidence() {

        testForecast.setConfidence(0.8F);
        assertEquals(0.8F, testForecast.getConfidence());
    }

    @Test
    public void testGetRationale() {assertSame("Good food", testForecast.getRationale());}

    @Test
    public void testSetRationale() {

        testForecast.setRationale("bad food");
        assertSame("bad food", testForecast.getRationale());
    }

    @Test
    public void testGetBundleList() {assertEquals(testBundles, testForecast.getBundleList());}

    @Test
    public void testSetBundleList() {

        Bundle testBundle = new Bundle(testSeller, "puddings", testItems.stream().limit(5).collect(Collectors.toCollection(ArrayList::new)),
                testAllergies.stream().limit(5).collect(Collectors.toCollection(ArrayList::new)), testTime, 7.50f, 10, 12,
                true, false);

        testBundles.add(testBundle);
        testForecast.setBundleList(testBundles);

        assertEquals(testBundles, testForecast.getBundleList());
    }

    @Test
    public void testGetReservationList() {assertEquals(testReservations, testForecast.getReservationList());}

    @Test
    public void testSetReservationList() {

        Bundle testBundle = new Bundle(testSeller, "puddings", testItems.stream().limit(5).collect(Collectors.toCollection(ArrayList::new)),
                testAllergies.stream().limit(5).collect(Collectors.toCollection(ArrayList::new)), testTime, 7.50f, 10, 12,
                true, false);
        Reservation testReservation = new Reservation(testBundle, testCustomer, testSeller, testTime, "AAAAAA",
                false, true, "rainy");

        testReservations.add(testReservation);
        testForecast.setReservationList(testReservations);

        assertEquals(testReservations, testForecast.getReservationList());
    }
}
