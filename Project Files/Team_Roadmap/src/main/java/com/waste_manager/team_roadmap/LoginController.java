package com.waste_manager.team_roadmap;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginController {
    @GetMapping("/sign_in")
    public String sign_in() {
        return "sign_in";
    }

    @GetMapping("/login_error")
    public String login_error(Model model) {
        model.addAttribute("error", "invalid username or password");
        return "sign_in";
    }
}
