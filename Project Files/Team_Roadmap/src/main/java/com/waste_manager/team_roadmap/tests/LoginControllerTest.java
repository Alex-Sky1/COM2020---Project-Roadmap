package com.waste_manager.team_roadmap.tests;

import com.waste_manager.team_roadmap.LoginController;
import com.waste_manager.team_roadmap.TeamRoadmapApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.assertj.MockMvcTester;


@WebMvcTest(LoginController.class)
public class LoginControllerTest {

    @Autowired
    private MockMvcTester mockMvcTester;

    // Verify the sign-in page loads
    @Test
    void testSignInPageLoad() {
        mockMvcTester.get()
                .uri("/sign_in")
                .assertThat()
                .hasStatusOk()
                .hasViewName("sign_in");
    }
}