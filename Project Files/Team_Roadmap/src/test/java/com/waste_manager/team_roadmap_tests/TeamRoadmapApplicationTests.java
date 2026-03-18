package com.waste_manager.team_roadmap_tests;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.resttestclient.autoconfigure.AutoConfigureRestTestClient;
import org.springframework.boot.test.context.SpringBootTest;
import com.waste_manager.team_roadmap.*;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.web.servlet.client.RestTestClient;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes=TeamRoadmapApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureRestTestClient
class TeamRoadmapApplicationTests {

    @LocalServerPort
    private int port;

    @Autowired
    private RestTestClient restTestClient;

    @Autowired
    private CustomerController customerController;
    @Autowired
    private SellerController sellerController;
    @Autowired
    private LoginController loginController;


    // Test that the application boots successfully and that the controllers exist
    @Test
    void contextLoads() {
        assertThat(customerController).isNotNull();
        assertThat(sellerController).isNotNull();
        assertThat(loginController).isNotNull();
    }
}
