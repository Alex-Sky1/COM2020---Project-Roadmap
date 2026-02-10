package com.waste_manager.team_roadmap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@SpringBootApplication
@Controller
public class TeamRoadmapApplication {
    public static void main(String[] args) {
        SpringApplication.run(TeamRoadmapApplication.class, args);
    }

    @RequestMapping("/")
    public String redirectRoot() {
        return "redirect:/sign_in";
    }

    @GetMapping("/sign_up_consumer")
    public String sign_up_consumer() {
        return "sign_up_consumer";
    }

    @GetMapping("/sign_up_seller")
    public String sign_up_seller() {
        return "sign_up_seller";
    }

    @GetMapping("/edit_profile_consumer")
    public String edit_profile_consumer() {
        return "edit_profile_consumer";
    }

    @GetMapping("/edit_profile_seller")
    public String edit_profile_seller() {
        return "edit_profile_seller";
    }

    @GetMapping("/dashboard_consumer")
    public String dashboard_consumer() {
        return "dashboard_consumer";
    }

    @GetMapping("/view_analytics_consumer")
    public String view_analytics_consumer() {
        return "view_analytics_consumer";
    }

    @GetMapping("/report_issue_consumer")
    public String report_issue_consumer() {
        return "report_issue_consumer";
    }

    @GetMapping("/dashboard_seller")
    public String dashboard_seller() {
        return "dashboard_seller";
    }

    @GetMapping("/post_bundle_seller")
    public String post_bundle_seller() {
        return "post_bundle_seller";
    }

    @GetMapping("/manage_bundles_seller")
    public String manage_bundles_seller() {
        return "manage_bundles_seller";
    }

    @GetMapping("/edit_bundle_seller")
    public String edit_bundle_seller() {
        return "edit_bundle_seller";
    }

    @GetMapping("/manage_reservations_seller")
    public String manage_reservations_seller() {
        return "manage_reservations_seller";
    }

    @GetMapping("/view_analytics_seller")
    public String view_analytics_seller() {
        return "view_analytics_seller";
    }

    @GetMapping("/forecasting_seller")
    public String forecasting_seller() {
        return "forecasting_seller";
    }
}




