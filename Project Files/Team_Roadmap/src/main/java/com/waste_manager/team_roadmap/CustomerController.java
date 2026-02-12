package com.waste_manager.team_roadmap;

import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


@Controller
public class CustomerController {

    private final CustomerRepository cr;
    private final SellerRepository sr;
    private final BundleRepository br;
    private final ReservationRepository rr;

    public CustomerController(CustomerRepository customerRepository, SellerRepository sellerRepository, BundleRepository bundleRepository, ReservationRepository reservationRepository) {
        this.cr = customerRepository;
        this.sr = sellerRepository;
        this.br = bundleRepository;
        this.rr = reservationRepository;
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

    @PostMapping("/edit_profile_consumer")
    public String editProfileCustomer(@RequestParam(value = "fname", required = false) String fname, @RequestParam(value = "sname", required = false) String sname,
                                    @RequestParam(value = "dname", required = false) String dname, @RequestParam(value = "address_line_1", required = false) String al1,
                                    @RequestParam(value = "postcode", required = false) String pcode, @RequestParam(value = "county", required = false) String county,
                                    @RequestParam(value = "email", required = false) String email, @RequestParam(value = "phone", required = false) String phone,
                                    @RequestParam(value = "password1", required = false) String pwd1, @RequestParam(value = "password2", required = false) String pwd2) {

        List<Seller> s = sr.findByDName(dname);
        List<Customer> c = cr.findByDName(dname);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = auth.getName();
        Customer customer = cr.findByDName(currentUsername).getFirst();
        long customerId = customer.getCustomerID();
        System.out.println("lergfhnkwjrgbnkj");
        if (!dname.isEmpty()) {
            if (!s.isEmpty() || !c.isEmpty()) {
                System.out.println("user name already exists");
            } else {
                cr.updateDNameById(dname, customerId);
            }
        }
        if (!pwd1.isEmpty() && pwd1.equals(pwd2)) {
            cr.updatePasswordById(pwd1, customerId);
        }
        if (!fname.isEmpty()) {
            cr.updateFNameById(fname, customerId);
        }
        if (!sname.isEmpty()) {
            cr.updateSNameById(sname, customerId);
        }
        if (!al1.isEmpty()) {
            cr.updateAddressById(al1, customerId);
        }
        if (!pcode.isEmpty()) {
            cr.updatePostcodeById(pcode, customerId);
        }
        if (!county.isEmpty()) {
            cr.updateCountyById(county, customerId);
        }
        if (!email.isEmpty()) {
            cr.updateEmailById(email, customerId);
        }
        if (!phone.isEmpty()) {
            cr.updatePhoneById(phone, customerId);
        }
        return "/edit_profile_consumer";
    }

    @GetMapping("/browse_bundles_consumer")
    public String browseBundlesConsumer(@RequestBody(required = false) ArrayList<String> filters, Model model) {
        List<Bundle> allBundles = br.findByReservedAndExpired(false, false);
        model.addAttribute("allBundles", allBundles);
        return "browse_bundles_consumer";
    }

    @PostMapping("/browse_bundles_consumer")
    public String reserveBundleConsumer(@RequestParam("postingID") int postingID, Model model) {
        Optional<Bundle> b = br.findById(postingID);
        Bundle b1 = b.get();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = auth.getName();
        Customer customer = cr.findByDName(currentUsername).getFirst();

        String claimCode = customer.generateClaimCode();
        Reservation r = new Reservation(b1, customer, b1.getSeller(), LocalDateTime.now(), claimCode, false, false, "someWeather");
        rr.save(r);
        b1.setReserved(true);
        br.setBundleReserved(true, b1.getPostingID());
        browseBundlesConsumer(new ArrayList<>(), model);
        return "browse_bundles_consumer";
    }
    @GetMapping("/manage_bundles_consumer")
    public String listReservedBundles(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = auth.getName();
        Customer c = cr.findByDName(currentUsername).getFirst();
        List<Reservation> reservations = rr.findByCustomerID(c.getCustomerID());
        model.addAttribute("reservations", reservations);
        return "/manage_bundles_consumer";
    }

    @PostMapping("/report_issue_consumer")
    public String reportIssue(){
        return "report_issue_consumer";
    }
    @PostMapping("/view_analytics_consumer")
    public String viewAnalyticsConsumer(){
        return "view_analytics_consumer";
    }



}
