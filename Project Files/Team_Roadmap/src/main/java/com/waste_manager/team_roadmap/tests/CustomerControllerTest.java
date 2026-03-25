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

// Create a controller test for the customer controller - WebMvcTest only loads controller-related methods which makes the testing quicker
@WebMvcTest(CustomerController.class)
public class CustomerControllerTest {

    // Load the repositories required using mockito beans to simulate them
    @Autowired
    private MockMvcTester mockMvcTester;
    @MockitoBean private CustomerRepository cr;
    @MockitoBean private SellerRepository sr;
    @MockitoBean private BundleRepository br;
    @MockitoBean private ReservationRepository rr;
    @MockitoBean private IssueReportRepository irr;
    @MockitoBean private AdminRepository ar;
    @MockitoBean private CustomUserDetailService cuds;

    // Assert the signup page loads
    @Test
    @WithMockUser(username = "testUser")
    void testConsumerSignUpPageLoads() {

        mockMvcTester.get()
                .uri("/sign_up_consumer")
                .assertThat()
                .hasStatusOk()
                .hasViewName("sign_up_consumer");
    }

    // Assert a customer can create an account
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

        // Create a user to allow user authentication for the application
        UserDetails mockUser = new User(
                "testUser",
                "encodedPassword",
                Collections.emptyList()
        );

        // When the CustomUserDetailService requests a user by username, load the mock user
        when(cuds.loadUserByUsername(anyString()))
                .thenReturn(mockUser);

        MockHttpSession session = new MockHttpSession();

        // Submit a request to change the user details
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

    @Test
    @WithMockUser(username = "testUser")
    void testBrowseBundleLoads() {

        Customer mockCustomer = new Customer("jim", "bob", "Jimmy", "no man's land",
                "NW1 6XE", "test", "test@gmail.com", "0000008776", "jim"
                , 6, new ArrayList<>(List.of(false, false, false, false, false)), true);

        mockCustomer.setCustomerID(1L);

        when(cr.findBydName("testUser")).thenReturn(new ArrayList<>(List.of(mockCustomer)));

        // Create a user to allow user authentication for the application
        UserDetails mockUser = new User(
                "testUser",
                "encodedPassword",
                Collections.emptyList()
        );

        // When the CustomUserDetailService requests a user by username, load the mock user
        when(cuds.loadUserByUsername(anyString()))
                .thenReturn(mockUser);

        mockMvcTester.get()
                .uri("/browse_bundles_consumer")
                .assertThat()
                .hasStatusOk()
                .hasViewName("browse_bundles_consumer");
    }



    /*
    @RequestParam(value="category", required = false) String category,
    @RequestParam(value = "postcode", required = false) String postcode,
    @RequestParam(value = "price_selector", required = false) String priceselector,
    @RequestParam(value="price", required = false) String price,
    @RequestParam(value = "time_selector", required = false) String timeSelector,
    @RequestParam(value = "time", required = false) String time,
    @RequestParam(value="celery", required = false) String celery,
    @RequestParam(value = "gluten", required = false) String gluten,
    @RequestParam(value = "crustaceans", required = false) String crustaceans,
    @RequestParam(value="eggs", required = false) String eggs,
    @RequestParam(value="fish", required = false) String fish,
    @RequestParam(value="lupin", required = false) String lupin,
    @RequestParam(value="milk", required = false) String milk,
    @RequestParam(value="molluscs", required = false) String molluscs,
    @RequestParam(value="mustard", required = false) String mustard,
    @RequestParam(value="peanuts", required = false) String peanuts,
    @RequestParam(value="sesame", required = false) String sesame,
    @RequestParam(value="soybeans", required = false) String soybeans,
    @RequestParam(value="sulphur", required = false) String sulphur,
    @RequestParam(value="nuts", required = false) String nuts)
     */
}
