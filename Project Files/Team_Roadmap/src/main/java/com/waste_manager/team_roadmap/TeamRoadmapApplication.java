package com.waste_manager.team_roadmap;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.IOException;

@SpringBootApplication
@Controller
public class TeamRoadmapApplication {
    public static void main(String[] args) {ConfigurableApplicationContext ctx = SpringApplication.run(TeamRoadmapApplication.class, args);}



    @RequestMapping("/")
    @DependsOn("temp_load_database")
    public String redirectRoot() {
        return "redirect:/sign_in";
    }

    @GetMapping("/sign_up_consumer")
    @DependsOn("temp_load_database")
    public String sign_up_consumer() {
        return "sign_up_consumer";
    }

    @GetMapping("/sign_up_seller")
    @DependsOn("temp_load_database")
    public String sign_up_seller() {
        return "sign_up_seller";
    }

    @GetMapping("/edit_profile_consumer")
    @DependsOn("temp_load_database")
    public String edit_profile_consumer() {
        return "edit_profile_consumer";
    }

    @GetMapping("/tos_consumer")
    @DependsOn("temp_load_database")
    public String tos_consumer() {
        return "tos_consumer";
    }

    @GetMapping("/tos_seller")
    @DependsOn("temp_load_database")
    public String tos_seller() {
        return "tos_seller";
    }

    @GetMapping("/privacy_policy_consumer")
    @DependsOn("temp_load_database")
    public String private_policy_consumer() {
        return "privacy_policy_consumer";
    }

    @GetMapping("/privacy_policy_seller")
    @DependsOn("temp_load_database")
    public String private_policy_seller() {
        return "privacy_policy_seller";
    }

    @GetMapping("/edit_profile_seller")
    @DependsOn("temp_load_database")
    public String edit_profile_seller() {
        return "edit_profile_seller";
    }

    @GetMapping("/dashboard_consumer")
    @DependsOn("temp_load_database")
    public String dashboard_consumer() {
        return "dashboard_consumer";
    }


    @GetMapping("/report_issue_consumer")
    @DependsOn("temp_load_database")
    public String report_issue_consumer() {
        return "report_issue_consumer";
    }

    @GetMapping("/dashboard_seller")
    @DependsOn("temp_load_database")
    public String dashboard_seller() {
        return "dashboard_seller";
    }

    @GetMapping("/post_bundle_seller")
    @DependsOn("temp_load_database")
    public String post_bundle_seller() {
        return "post_bundle_seller";
    }

    @GetMapping("/edit_bundle_seller")
    @DependsOn("temp_load_database")
    public String edit_bundle_seller() {
        return "edit_bundle_seller";
    }





}




