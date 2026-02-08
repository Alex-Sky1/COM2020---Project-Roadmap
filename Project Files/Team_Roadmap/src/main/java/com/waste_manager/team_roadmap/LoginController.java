package com.waste_manager.team_roadmap;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class LoginController {
    private final SellerRepository sellerRepository;
    private final CustomerRepository customerRepository;
    LoginController(SellerRepository sellerRespository, CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
        this.sellerRepository = sellerRespository;
    }
    @GetMapping("/sign_in")
    public String sign_in() {
        return "sign_in";
    }
}
