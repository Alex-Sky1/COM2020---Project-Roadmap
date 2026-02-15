package com.waste_manager.team_roadmap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
public class WebSecurityConfig {
    private final CustomAuthenticationSuccessHandler successHandler;
    public WebSecurityConfig(CustomAuthenticationSuccessHandler successHandler, CustomUserDetailService userDetailService) {
            this.successHandler = successHandler;

    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) {
        http.authorizeHttpRequests((requests)->requests.requestMatchers("/", "/sign_up_consumer", "/sign_up_seller", "/styling.css", "/images/**","/js/**", "/tos_consumer", "/tos_seller", "/private_policy_consumer", "/private_policy_seller").permitAll() //set pages which all users access
                .requestMatchers("/dashboard_seller", "/edit_bundle_seller", "/edit_profile_seller", "/post_bundle_seller", "/manage_bundles_seller", "/manage_reservations_seller", "/view_analytics_seller", "/forecasting_seller").hasRole("SELLER") //set pages seller can access
                .requestMatchers("/dashboard_consumer", "/edit_profile_consumer", "/view_analytics_consumer", "/manage_bundles_consumer", "/browse_bundles_consumer", "/report_issue_consumer").hasRole("CUSTOMER") //set pages customer can access
                .anyRequest().authenticated()).formLogin((form)->form.loginPage("/sign_in").permitAll().successHandler(successHandler)).logout(LogoutConfigurer::permitAll); //login using /signup page and allow logout
        return http.build();
    }


}
