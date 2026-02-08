package com.waste_manager.team_roadmap;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Controller
public class SellerController {
    private final SellerRepository sr;
    private final CustomerRepository cr;

    public SellerController(SellerRepository sellerRepository, CustomerRepository customerRepository) {
        this.sr = sellerRepository;
        this.cr = customerRepository;

    }

    @PostMapping("/sign_up_seller")
    public String signup(@RequestParam("fname") String fname, @RequestParam("sname") String sname,
                         @RequestParam("business") String business, @RequestParam("address_line_1") String al1,
                         @RequestParam("postcode") String pcode, @RequestParam("county") String county,
                         @RequestParam("email") String email, @RequestParam("phone") String phone,
                         @RequestParam("password1") String pwd1, @RequestParam("password2") String pwd2,
                         Model model) {

        List<Seller> s = sr.findBydname(business);
        List<Customer> c = cr.findByDisplayName(business);
        if(!pwd1.equals(pwd2)){
            System.out.println("passwords don't match");
            return "/sign_up_seller";
        }
        if(fname.isEmpty() || sname.isEmpty() || business.isEmpty() || al1.isEmpty() ||  pcode.isEmpty() || county.isEmpty() || email.isEmpty() || phone.isEmpty() || pwd1.isEmpty()){
            System.out.println("Please fill all the fields");
            return "/sign_up_seller";
        }
        if (!s.isEmpty() || !c.isEmpty()) {
            System.out.println("user name already exists");
            return "/sign_up_seller";
        } else {
            Seller s1 = new Seller(fname, sname, business, al1, pcode, county, email, phone, pwd1);
            System.out.println("success");
            sr.save(s1);
            return "/sign_in";
        }

    }


}
