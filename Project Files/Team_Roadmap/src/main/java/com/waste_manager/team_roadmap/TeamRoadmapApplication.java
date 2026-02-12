package com.waste_manager.team_roadmap;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @GetMapping("/tos_consumer")
    public String tos_consumer() {
        return "tos_consumer";
    }

    @GetMapping("/tos_seller")
    public String tos_seller() {
        return "tos_seller";
    }

    @GetMapping("/private_policy_consumer")
    public String private_policy_consumer() {
        return "privacy_policy_consumer";
    }

    @GetMapping("/private_policy_seller")
    public String private_policy_seller() {
        return "privacy_policy_seller";
    }

    @GetMapping("/edit_profile_seller")
    public String edit_profile_seller() {
        return "edit_profile_seller";
    }

    @GetMapping("/dashboard_consumer")
    public String dashboard_consumer() {
        return "dashboard_consumer";
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

    @GetMapping("/edit_bundle_seller")
    public String edit_bundle_seller() {
        return "edit_bundle_seller";
    }

    @GetMapping("/view_analytics_seller")
    public String view_analytics_seller() {
        return "view_analytics_seller";
    }

}




