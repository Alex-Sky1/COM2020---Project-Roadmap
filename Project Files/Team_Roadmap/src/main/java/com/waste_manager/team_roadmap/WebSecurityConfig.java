package com.waste_manager.team_roadmap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
public class WebSecurityConfig {
    private final CustomAuthenticationSuccessHandler successHandler;
    public WebSecurityConfig(CustomAuthenticationSuccessHandler successHandler) {
            this.successHandler = successHandler;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
        String[] permitAllPages = {"/", "/sign_up_consumer", "/sign_up_seller", "/styling.css", "/images/**","/js/**", "/tos_consumer", "/tos_seller", "/privacy_policy_consumer", "/privacy_policy_seller"};
        String[] permitSeller = {"/dashboard_seller", "/edit_bundle_seller", "/edit_profile_seller", "/post_bundle_seller", "/manage_bundles_seller", "/manage_reservations_seller", "/view_analytics_seller", "/forecasting_seller", "/manage_issue_seller"};
        String[] permitCustomer = {"/dashboard_consumer", "/edit_profile_consumer", "/view_analytics_consumer", "/manage_bundles_consumer", "/browse_bundles_consumer", "/report_issue_consumer", "/view_issue_consumer"};
        String[] permitAdmin = {};
        http.authorizeHttpRequests((requests)->requests.requestMatchers(permitAllPages).permitAll() //set pages which all users access
                .requestMatchers(permitSeller).hasAnyRole("SELLER", "ADMIN") //set pages seller can access
                .requestMatchers(permitCustomer).hasAnyRole("CUSTOMER", "ADMIN") //set pages customer can access
                .requestMatchers(permitAdmin).hasAnyRole("ADMIN")
                .anyRequest().authenticated()).formLogin((form)->form.loginPage("/sign_in").permitAll().successHandler(successHandler)).logout(LogoutConfigurer::permitAll); //login using /signup page and allow logout
        return http.build();
    }
}
