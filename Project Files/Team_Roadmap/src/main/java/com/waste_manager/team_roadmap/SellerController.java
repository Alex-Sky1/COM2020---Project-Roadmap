package com.waste_manager.team_roadmap;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.*;
import java.time.LocalDateTime;

@Controller
public class SellerController {
    private final SellerRepository sr;
    private final CustomerRepository cr;
    private final BundleRepository br;

    public SellerController(SellerRepository sellerRepository, CustomerRepository customerRepository, BundleRepository bundleRepository) {
        this.sr = sellerRepository;
        this.cr = customerRepository;
        this.br = bundleRepository;

    }

    @PostMapping("/sign_up_seller")
    public String signup(@RequestParam("fname") String fname, @RequestParam("sname") String sname,
                         @RequestParam("business") String business, @RequestParam("address_line_1") String al1,
                         @RequestParam("postcode") String pcode, @RequestParam("county") String county,
                         @RequestParam("email") String email, @RequestParam("phone") String phone,
                         @RequestParam("password1") String pwd1, @RequestParam("password2") String pwd2) {

        List<Seller> s = sr.findByDName(business);
        List<Customer> c = cr.findByDName(business);
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

    @PostMapping("/post_bundle_seller")
    public String postBundle(@RequestParam("category") String category, @RequestParam("price") String price,
                             @RequestParam("pickup") String pickup,
                             @RequestParam(value="celery", required = false) String celery,
                             @RequestParam(value = "gluten", required = false) String gluten,
                             @RequestParam(value = "crustaceans", required = false) String crustaceans,
                             @RequestParam(value="eggs", required = false) String eggs,
                             @RequestParam(value="fish", required = false) String fish,
                             @RequestParam(value="lupin", required = false) String lupin,
                             @RequestParam(value="milk", required = false) String milk,
                             @RequestParam(value="molluscs", required = false) String molluscs,
                             @RequestParam(value="mustard", required = false) String mustard,
                             @RequestParam(value="peanuts", required = false) String peanuts,
                             @RequestParam(value="sesame", required = false) String sesame,
                             @RequestParam(value="soybeans", required = false) String soybeans,
                             @RequestParam(value="sulphur", required = false) String sulphur,
                             @RequestParam(value="nuts", required = false) String nuts){

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = auth.getName();
        Seller s = sr.findByDName(currentUsername).getFirst();
        ArrayList<String> allergens = new ArrayList<>();
        if(celery!= null) allergens.add(celery);
        if(gluten!= null) allergens.add(gluten);
        if(crustaceans != null) allergens.add(crustaceans);
        if(eggs != null) allergens.add(eggs);
        if(fish != null) allergens.add(fish);
        if(lupin != null) allergens.add(lupin);
        if(milk != null) allergens.add(milk);
        if(molluscs != null) allergens.add(molluscs);
        if(mustard != null) allergens.add(mustard);
        if(peanuts != null) allergens.add(peanuts);
        if(sesame != null) allergens.add(sesame);
        if(soybeans != null) allergens.add(soybeans);
        if(sulphur != null) allergens.add(sulphur);
        if(nuts != null) allergens.add(nuts);

        for(String allergen : allergens){
            System.out.println(allergen);
        }

        ArrayList<String> content = new ArrayList<>();
        int pickupHr = Integer.parseInt(pickup.substring(0,2));

        Bundle bundle = new Bundle(s, category,  content, allergens, 1, Float.parseFloat(price), 0, pickupHr, false, false);
        br.save(bundle);
        return "/post_bundle_seller";
    }


    @PostMapping("edit_profile_seller")
    public String editProfileSeller(@RequestParam(value = "fname") String fname, @RequestParam("sname") String sname,
                                    @RequestParam("business") String business, @RequestParam("address_line_1") String al1,
                                    @RequestParam("postcode") String pcode, @RequestParam("county") String county,
                                    @RequestParam("email") String email, @RequestParam("phone") String phone,
                                    @RequestParam("password1") String pwd1, @RequestParam("password2") String pwd2){
        List<Seller> s = sr.findByDName(business);
        List<Customer> c = cr.findByDName(business);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = auth.getName();
        Seller seller = sr.findByDName(currentUsername).getFirst();
        int sellerId= seller.getSellerID();

//        if(!business.isEmpty()){
//            if (!s.isEmpty() || !c.isEmpty()) {
//                System.out.println("user name already exists");
//            } else {
//                sr.updateDNameById(business, sellerId);
//            }
//        }
//        if(!pwd1.isEmpty() && pwd1.equals(pwd2)){
//            //sr.upd(pwd1, sellerId);
//        }
//        if(!fname.isEmpty()){
//            sr.updateFNameById(fname, sellerId);
//        }
//        if(!sname.isEmpty()){
//            sr.updateSNameById(sname, sellerId);
//        }
//        if(!al1.isEmpty()){
//            sr.updateAddressById(al1, sellerId);
//        }
//        if(!pcode.isEmpty()){
//            sr.updatePostcodeById(pcode, sellerId);
//        }
//        if(!county.isEmpty()){
//            sr.updateCountyById(county, sellerId);
//        }
//        if(!email.isEmpty()){
//            sr.updateEmailById(email, sellerId);
//        }
//        if(!phone.isEmpty()){
//            sr.updatePhoneById(phone, sellerId);
//        }

        return "/edit_profile_seller";
    }
    @PostMapping("/manage_bundles_seller")
    public String manageBundles(){
        return  "/manage_bundles_seller";
    }
    @PostMapping("/edit_bundle_seller")
    public String editBundle(){
        return "/edit_bundle_seller";
    }

    @GetMapping("/forecasting_seller")
    public String forecasting_seller() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = auth.getName();
        Seller s = sr.findByDName(currentUsername).getFirst();

        LocalDateTime now = LocalDateTime.now();
        String day = now.getDayOfWeek().name();
        DayOfWeek dayOfWeek = DayOfWeek.valueOf(day);
        //Forecast f = new Forecast(dayOfWeek, now, s.getSellerID(),  )
        return "forecasting_seller";
    }
    @PostMapping("/forecasting_seller")
    public String forecastingSeller(){
        return "/forecasting_seller";
    }

    @PostMapping("manage_reservations_seller")
    public String manageReservationsSeller(){
        return "/manage_reservations_seller";
    }
    @PostMapping("view_analytics_seller")
    public String viewAnalyticsSeller(){
        return "/view_analytics_seller";
    }
}
