package com.waste_manager.team_roadmap;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.ArrayList;
import java.util.List;



@Controller
public class CustomerController {

    private final CustomerRepository cr;
    private final SellerRepository sr;

    public CustomerController(CustomerRepository customerRepository, SellerRepository sellerRepository) {
        this.cr = customerRepository;
        this.sr = sellerRepository;
    }

    @PostMapping("/sign_up_consumer")
    public String signup(@RequestParam("fname") String fname, @RequestParam("sname") String sname,
                         @RequestParam("dname") String dname, @RequestParam("address_line_1") String al1,
                         @RequestParam("postcode") String pcode, @RequestParam("county") String county,
                         @RequestParam("email") String email, @RequestParam("phone") String phone,
                         @RequestParam("password1") String pwd1, @RequestParam("password2") String pwd2,
                         Model model) {

        List<Customer> c = cr.findByDisplayName(dname);
        List<Seller> s = sr.findBydname(dname);

        if(!pwd1.equals(pwd2)){
            System.out.println("passwords don't match");
            return "/sign_up_consumer";
        }
        if(fname.isEmpty() || sname.isEmpty() || dname.isEmpty() || al1.isEmpty() ||  pcode.isEmpty() || county.isEmpty() || email.isEmpty() || phone.isEmpty() || pwd1.isEmpty()){
            System.out.println("Please fill all the fields");
            return "/sign_up_consumer";
        }
        if (!s.isEmpty() || !c.isEmpty()) {
            System.out.println("user name already exists");
            return "/sign_up_consumer";
        } else {
            Customer c1 = new Customer(fname, sname, dname, al1, pcode, county, email, phone, pwd1, 0, new ArrayList<Boolean>());
            cr.save(c1);
            System.out.println("sign up successful");
            return "/sign_in";
        }
    }

    @PostMapping("/browse_bundles_consumer")
    public String browseBundlesConsumer(@RequestBody(required = false) ArrayList<String> filters, Model model, BundleRepository bundleRepository) {
        ArrayList<Bundle> allBundles = new ArrayList<>();//(bundleRepository.findAllOrderByPrice());
        model.addAttribute("allBundles", allBundles);
        return "browse_bundles_consumer";
    }




}
