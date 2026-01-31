package com.waste_manager.team_roadmap;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.util.Assert;
import static org.junit.jupiter.api.Assertions.*;

public class ForecastTest {

    // Ran once before any of the tests
    @BeforeAll
    public static void setup() throws IllegalPackSizeException {
        Forecast testForecast = new Forecast(); // Constructor doesn't exist currently
    }

    // Verifies the ID of a bundle
    @Test
    public void testGetPostID() {

        Assert.assertTrue(testForecast.getPostID == 1);
    }
}
