package com.waste_manager.team_roadmap.tests;

import com.waste_manager.team_roadmap.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

    @Autowired
    private MockMvcTester mockMvcTester;

    @MockitoBean private CustomerRepository cr;
    @MockitoBean private SellerRepository sr;
    @MockitoBean private BundleRepository br;
    @MockitoBean private ReservationRepository rr;
    @MockitoBean private IssueReportRepository irr;
    @MockitoBean private AdminRepository ar;
    @MockitoBean private CustomUserDetailService cuds;

    @Test
    @WithMockUser(username = "testUser")
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
                .param("accept", "on")
                .assertThat()
                .hasStatusOk()
                .hasViewName("sign_in");
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
    @WithMockUser(username = "testUser")
    void testEditProfile() {

        // Create a mock customer for the customer repository to interact with

        Customer mockCustomer = new Customer("jim", "bob", "Jimmy", "no man's land",
                "NW1 6XE", "test", "test@gmail.com", "0000008776", "jim"
                , 6, new ArrayList<>(List.of(false, false, false, false, false)), true);

        mockCustomer.setCustomerID(1L);

        when(cr.findBydName("testUser")).thenReturn(new ArrayList<>(List.of(mockCustomer)));

        // Create a user for the session so the
        UserDetails mockUser = new User(
                "testUser",
                "encodedPassword",
                Collections.emptyList()
        );

        when(cuds.loadUserByUsername(anyString()))
                .thenReturn(mockUser);

        MockHttpSession session = new MockHttpSession();

        mockMvcTester.post()
                .uri("/edit_profile_consumer")
                .session(session)
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
