package com.waste_manager.team_roadmap_tests;

import com.waste_manager.team_roadmap.CustomerController;
import com.waste_manager.team_roadmap.TeamRoadmapApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ContextConfiguration(classes = TeamRoadmapApplication.class)
@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

    @Autowired
    private MockMvcTester mockMvcTester;

    @Test
    void testConsumerSignUpPageLoads() {

        mockMvcTester.get()
                .uri("/sign_up_consumer")
                .assertThat()
                .hasStatusOk()
                .hasViewName("sign_in");
    }
}
