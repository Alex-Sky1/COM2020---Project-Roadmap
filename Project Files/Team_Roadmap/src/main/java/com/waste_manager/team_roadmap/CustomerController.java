package com.waste_manager.team_roadmap;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
                         @RequestParam("password1") String pwd1, @RequestParam("password2") String pwd2) {

        List<Customer> c = cr.findByDName(dname);
        List<Seller> s = sr.findByDName(dname);

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

//    @PostMapping("edit_profile_consumer")
//    public String editProfileCustomer(@RequestParam("fname") String fname, @RequestParam("sname") String sname,
//                                    @RequestParam("dname") String dname, @RequestParam("address_line_1") String al1,
//                                    @RequestParam("postcode") String pcode, @RequestParam("county") String county,
//                                    @RequestParam("email") String email, @RequestParam("phone") String phone,
//                                    @RequestParam("password1") String pwd1, @RequestParam("password2") String pwd2) {
//        List<Seller> s = sr.findByDname(dname);
//        List<Customer> c = cr.findByDisplayName(dname);
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        String currentUsername = auth.getName();
//        Customer customer = cr.findByDisplayName(currentUsername).getFirst();
//        long customerId = customer.getCustomerID();
//
//        if (!dname.isEmpty()) {
//            if (!s.isEmpty() || !c.isEmpty()) {
//                System.out.println("user name already exists");
//            } else {
//                cr.updateDisplayNameById(dname, customerId);
//            }
//        }
//        if (!pwd1.isEmpty() && pwd1.equals(pwd2)) {
//            cr.updatePasswordById(pwd1, customerId);
//        }
//        if (!fname.isEmpty()) {
//            cr.updateFnameById(fname, customerId);
//        }
//        if (!sname.isEmpty()) {
//            cr.updateSnameById(sname, customerId);
//        }
//        if (!al1.isEmpty()) {
//            cr.updateAddressById(al1, customerId);
//        }
//        if (!pcode.isEmpty()) {
//            cr.updatePasswordById(pcode, customerId);
//        }
//        if (!county.isEmpty()) {
//            cr.updateCountyById(county, customerId);
//        }
//        if (!email.isEmpty()) {
//            cr.updateEmailById(email, customerId);
//        }
//        if (!phone.isEmpty()) {
//            cr.updatePhoneById(phone, customerId);
//        }
//        return "/edit_profile_consumer";
//    }

        @PostMapping("/browse_bundles_consumer")
    public String browseBundlesConsumer(@RequestBody(required = false) ArrayList<String> filters, Model model) {
        ArrayList<Bundle> allBundles = new ArrayList<>();//(bundleRepository.findAllOrderByPrice());
        model.addAttribute("allBundles", allBundles);
        return "browse_bundles_consumer";
    }

    @PostMapping("/report_issue_consumer")
    public String reportIssue(){
        return "report_issue_consumer";
    }
    @PostMapping("/view_analytics_consumer")
    public String viewAnalyticsConsumer(){
        return "view_analytics_consumer";
    }
    @PostMapping("/manage_bundles_consumer")
    public String manageBundlesConsumer(){
        return "manage_bundles_consumer";
    }


}
