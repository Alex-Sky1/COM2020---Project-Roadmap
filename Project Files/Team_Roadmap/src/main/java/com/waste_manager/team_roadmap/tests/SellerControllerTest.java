package com.waste_manager.team_roadmap.tests;

import com.waste_manager.team_roadmap.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@WebMvcTest
public class SellerControllerTest {

    @Autowired
    private MockMvcTester mockMvcTester;
    @MockitoBean
    private CustomerRepository cr;
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
                .uri("/sign_up_seller")
                .assertThat()
                .hasStatusOk()
                .hasViewName("sign_up_seller");
    }

    @Test
    void testLoginSubmit() {
        mockMvcTester.post()
                .uri("/sign_up_seller")
                .param("fname", "Gordon")
                .param("sname", "Ramsey")
                .param("business", "Hell's Kitchen")
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
    @WithMockUser(username = "testUser")
    void testEditProfile() {

        // Create a mock seller for the seller repository to interact with
        Seller mockSeller = new Seller("Peter", "Pan", "Pete's Pancakes", "Neverland", "NV21 TK2", "Crocodile Creek",
                "Peter.Pan12@hookmail.com", "06847 268425", "T1nkerb3ll!", true);
        MockHttpSession session = new MockHttpSession();

        mockSeller.setSellerID(1);
        when(sr.findBydName("testUser")).thenReturn(new ArrayList<>(List.of(mockSeller)));

        // Create a user to allow user authentication for the application
        UserDetails mockUser = new User(
                "testUser",
                "encodedPassword",
                Collections.emptyList()
        );

        when(cuds.loadUserByUsername(anyString()))
                .thenReturn(mockUser);


        // Submit a request to change the user details
        mockMvcTester.post()
                .uri("/edit_profile_seller")
                .session(session)
                .param("fname", "Gordon")
                .param("sname", "Ramsey")
                .param("business", "Hell's Kitchen")
                .param("address_line_1", "27 Wren Drive")
                .param("postcode", "B10 8SZ")
                .param("county", "West Midlands")
                .param("email", "RamseyG32@HellsKitchen.gg")
                .param("phone", "01967 425395")
                .param("password1", "1diotSandw!tch")
                .param("password2", "1diotSandw!tch")
                .assertThat()
                .hasStatusOk()
                .hasViewName("edit_profile_seller");
    }

    @Test
    @WithMockUser("testUser")
    void testPostBundle() {

        // Create a mock seller for the seller repository to interact with
        Seller mockSeller = new Seller("Peter", "Pan", "Pete's Pancakes", "Neverland", "NV21 TK2", "Crocodile Creek",
                "Peter.Pan12@hookmail.com", "06847 268425", "T1nkerb3ll!", true);
        MockHttpSession session = new MockHttpSession();

        mockSeller.setSellerID(1);
        when(sr.findBydName("testUser")).thenReturn(new ArrayList<>(List.of(mockSeller)));

        // Create a user to allow user authentication for the application
        UserDetails mockUser = new User(
                "testUser",
                "encodedPassword",
                Collections.emptyList()
        );

        when(cuds.loadUserByUsername(anyString()))
                .thenReturn(mockUser);


        when(cuds.loadUserByUsername(anyString()))
                .thenReturn(mockUser);

        mockMvcTester.post()
                .uri("/post_bundle_seller")
                .session(session)
                .param("category", "Fish & Meat")
                .param("price", "2.98")
                .param("pickup", "12")
                .param("bundle_numbers", "1")
                .param("discount", "0.5")
                .param("hidden_items", "0")
                .param("fish", "1")
                .param("nuts", "1")
                .assertThat()
                .hasStatusOk()
                .hasViewName("post_bundle_seller");
    }
}
