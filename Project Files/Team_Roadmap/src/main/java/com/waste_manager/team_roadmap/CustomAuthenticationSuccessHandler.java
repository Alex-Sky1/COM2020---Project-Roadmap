package com.waste_manager.team_roadmap;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CustomAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    @Override
    // Used to send different user roles to different locations
    public void onAuthenticationSuccess(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response, Authentication authentication) throws IOException {

        // If role is seller send to seller dashboard, otherwise send to consumer dashboard
        if(authentication.getAuthorities().iterator().next().getAuthority().equals("ROLE_SELLER")) {
            response.sendRedirect("/dashboard_seller");
        } else {
            response.sendRedirect("/dashboard_consumer");
        }


    }
}
