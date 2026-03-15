package com.waste_manager.team_roadmap_tests;

import com.waste_manager.team_roadmap.CustomerController;
import com.waste_manager.team_roadmap.TeamRoadmapApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.web.bind.annotation.RequestParam;

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

    @Test
    void testLoginSubmit() {
        mockMvcTester.post()
                .uri("/sign_up_consumer")
                .param("fname", "Gordon")
                .param("sname", "Ramsey")
                .param("dname", "Gordon Ramsey")
                .param("address_line_1", "26 Wren Drive")
                .param("postcode", "B10 8SV")
                .param("county", "West Midlands")
                .param("email", "RamseyG32@HellsKitchen.gg")
                .param("phone", "01967 425395")
                .param("password1", "1diotSandw!tch")
                .param("password2", "1diotSandw!tch")
                .param("accept", "accept")
                .assertThat()
                .hasStatusOk();
    }
//
//    (@RequestParam("fname") String fname, @RequestParam("sname") String sname,
//    @RequestParam("dname") String dname, @RequestParam("address_line_1") String al1,
//    @RequestParam("postcode") String pcode, @RequestParam("county") String county,
//    @RequestParam("email") String email, @RequestParam("phone") String phone,
//    @RequestParam("password1") String pwd1, @RequestParam("password2") String pwd2,
//    @RequestParam(value = "accept", required = false) String tosAccept, Model model)
}
