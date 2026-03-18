package com.waste_manager.team_roadmap.tests;

import com.waste_manager.team_roadmap.CustomerController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.web.servlet.assertj.MockMvcTester;


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
                .hasViewName("sign_up_consumer");
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
                .hasStatusOk()
                .hasViewName("sign_in_consumer");
    }

    @Test
    void testIncorrectLoginSubmit() {

        mockMvcTester.post()
                .uri("/sign_up_consumer")
                .param("fname", "")
                .param("sname", "")
                .param("dname", "")
                .param("address_line_1", "")
                .param("postcode", "")
                .param("county", "")
                .param("email", "")
                .param("phone", "")
                .param("password1", "")
                .param("password2", "")
                .param("accept", "")
                .assertThat()
                .hasStatusOk()
                .hasViewName("sign_up_consumer");
    }

    @Test
    void testEditProfileLoads() {

        mockMvcTester.get()
                .uri("/edit_profile_consumer")
                .assertThat()
                .hasStatusOk()
                .hasViewName("edit_profile_consumer");
    }

    @Test
    void testEditProfile() {

        mockMvcTester.post()
                .uri("/edit_profile_consumer")
                .param("fname", "Steven")
                .param("sname", "Scott")
                .param("dname", "Steven Scott")
                .param("address_line_1", "26 Hawk Drive")
                .param("postcode", "B11 8SV")
                .param("county", "East Midlands")
                .param("email", "ScottStevie1@HellsKitchen.gg")
                .param("phone", "01967 487369")
                .param("password1", "uin&mklnk{0i!A")
                .param("password2", "uin&mklnk{0i!A")
                .assertThat()
                .hasStatusOk()
                .hasViewName("edit_profile_consumer");
    }

    /*
    @RequestParam(value = "fname", required = false) String fname, @RequestParam(value = "sname", required = false) String sname,
    @RequestParam(value = "dname", required = false) String dname, @RequestParam(value = "address_line_1", required = false) String al1,
    @RequestParam(value = "postcode", required = false) String pcode, @RequestParam(value = "county", required = false) String county,
    @RequestParam(value = "email", required = false) String email, @RequestParam(value = "phone", required = false) String phone,
    @RequestParam(value = "password1", required = false) String pwd1, @RequestParam(value = "password2", required = false) String pwd2,
     */
}
