package com.waste_manager.team_roadmap.tests;

import com.waste_manager.team_roadmap.CustomerController;
import com.waste_manager.team_roadmap.LoginController;
import com.waste_manager.team_roadmap.SellerController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.web.servlet.client.RestTestClient;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TeamRoadmapApplicationTests {

    @LocalServerPort
    private int port;

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
