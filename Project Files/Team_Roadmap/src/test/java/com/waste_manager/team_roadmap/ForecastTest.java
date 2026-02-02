package com.waste_manager.team_roadmap;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

public class ForecastTest {

    static Forecast testForecast;

    // Ran once before any of the tests
    @BeforeAll
    public static void setup() {
        testForecast = new Forecast(); // Constructor doesn't exist currently
    }

    // Verifies the ID of a bundle
    @Test
    public void testGetDayOfWeek() {

        assertEquals(DayOfWeek.MONDAY, testForecast.getDay());
    }

    @Test
    public void testGetTime() {

        assertEquals(LocalDateTime.now(), testForecast.getTime()); // Placeholder expected
    }

    @Test
    public void testGetSellerID() {

        assertEquals(1, testForecast.getSellerID());
    }

    @Test
    public void testGetCategory() {

        assertSame("Pastry", testForecast.getCategory());
    }

    @Test
    public void testGetPrice() {

        assertEquals(10.50F, testForecast.getPrice());
    }

    @Test
    public void testGetDiscount() {

        assertEquals(10, testForecast.getDiscount());
    }

    @Test
    public void testGetWeatherFlag() {

        assertSame("rainy", testForecast.getWeatherFlag());
    }

    @Test
    public void testGetPostingID() {

        assertEquals(1, testForecast.getPostingID());
    }

    @Test
    public void testGetObservedRes() {

        assertEquals(5, testForecast.getObservedRes());
    }

    @Test
    public void testGetObservedNoShow() {

        assertEquals(1, testForecast.getObservedNoShow());
    }

    @Test
    public void testGetPredictedRes() {

        assertEquals(1, testForecast.getPredictedRes());
    }

    @Test
    public void testGetPredictedNoShow() {

        assertEquals(1, testForecast.getPredictedNoShow());
    }

    @Test
    public void testGetConfidence() {

        assertEquals(0.5F, testForecast.getConfidence());
    }

    @Test
    public void testGetRationale() {

        assertSame("Good food", testForecast.getRationale());
    }

    ///

    @Test
    public void testSetDayOfWeek() {

        testForecast.setDay(DayOfWeek.SATURDAY);
        assertEquals(DayOfWeek.SATURDAY, testForecast.getDay());
    }

    @Test
    public void testSetTime() {

        testForecast.setTime(LocalDateTime.of(2026, 2, 1, 15, 0));
        assertEquals(LocalDateTime.of(2026, 2, 1, 15, 0), testForecast.getTime());
    }

    @Test
    public void testSetSellerID() {

        testForecast.setSellerID(10);
        assertEquals(10, testForecast.getSellerID());
    }

    @Test
    public void testSetCategory() {

        testForecast.setCategory("Pudding");
        assertSame("Pudding", testForecast.getCategory());
    }

    @Test
    public void testSetPrice() {

        testForecast.setPrice(11.01F);
        assertEquals(11.01F, testForecast.getPrice());
    }

    @Test
    public void testSetPostingID(){

        testForecast.setPostingID(12);
        assertEquals(12, testForecast.getPostingID());
    }

    @Test
    public void testSetDiscount() {

        testForecast.setDiscount(20);
        assertEquals(20, testForecast.getDiscount());
    }

    @Test
    public void testSetWeatherFlag() {

        testForecast.setWeatherFlag("sunny");
        assertSame("sunny", testForecast.getWeatherFlag());
    }

    @Test
    public void testSetObservedRes() {

        testForecast.setObservedRes(2);
        assertEquals(2, testForecast.getObservedRes());
    }

    @Test
    public void testSetObservedNoShow() {

        testForecast.setObservedNoShow(2);
        assertEquals(2, testForecast.getObservedNoShow());
    }

    @Test
    public void testSetPredictedRes() {

        testForecast.setPredictedRes(2);
        assertEquals(2, testForecast.getPredictedRes());
    }

    @Test
    public void testSetPredictedNoShow() {

        testForecast.setPredictedNoShow(2);
        assertEquals(2, testForecast.getPredictedNoShow());
    }

    @Test
    public void testSetConfidence() {

        testForecast.setConfidence(0.8F);
        assertEquals(0.8F, testForecast.getConfidence());
    }

    @Test
    public void testSetRationale() {

        testForecast.setRationale("bad food");
        assertSame("bad food", testForecast.getRationale());
    }
}
