package com.waste_manager.team_roadmap;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.Arrays;
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
                         @RequestParam("password1") String pwd1, @RequestParam("password2") String pwd2,
                         @RequestParam(value = "accept", required = false) String tosAccept) {


        //check Passwords match
        if(!pwd1.equals(pwd2)){
            System.out.println("passwords don't match");
            return "sign_up_consumer";
        }
        //if any field is empty don't allow sign up
        if(fname.isEmpty() || sname.isEmpty() || dname.isEmpty() || al1.isEmpty() ||  pcode.isEmpty() || county.isEmpty() || email.isEmpty() || phone.isEmpty() || pwd1.isEmpty()){
            System.out.println("Please fill all the fields");
            return "sign_up_consumer";
        }
        //check that no other seller or customer is using that username
        List<Customer> c = cr.findByDName(dname);
        List<Seller> s = sr.findByDName(dname);
        if (!s.isEmpty() || !c.isEmpty()) {
            System.out.println("user name already exists");
            return "sign_up_consumer";
        }
        if(tosAccept==null){
            System.out.println("please accept the terms and conditions");
            return "sign_up_consumer";
        }else {
            //create and save new customer
            Customer c1 = new Customer(fname, sname, dname, al1, pcode, county, email, phone, pwd1, 0, new ArrayList<Boolean>(), true);
            if(!c1.validatePassword(pwd1)) {
                model.addAttribute("error", "Invalid password");
            }
            else {
                cr.save(c1);
                System.out.println("sign up successful");
                return "sign_in";
            }
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
        //get current user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = auth.getName();
        Customer customer = cr.findByDName(currentUsername).get(0);
        long customerId = customer.getCustomerID();

        //if username is already being used don't allow
        if (!dname.isEmpty()) {
            if (!s.isEmpty() || !c.isEmpty()) {
                System.out.println("user name already exists");
            } else {
                cr.updateDNameById(dname, customerId);
            }
        }
        //check passwords match
        if (!pwd1.isEmpty() && pwd1.equals(pwd2)) {
            if(!customer.validatePassword(pwd1)) {
                model.addAttribute("error", "Invalid password");
            }else {
                cr.updatePasswordById(pwd1, customerId);
            }
        }
        else{
            model.addAttribute("error", "Passwords don't match");
        }
        //update first name
        if (!fname.isEmpty()) {
            cr.updateFNameById(fname, customerId);
        }
        //update surname
        if (!sname.isEmpty()) {
            cr.updateSNameById(sname, customerId);
        }
        //update address
        if (!al1.isEmpty()) {
            cr.updateAddressById(al1, customerId);
        }
        //update postcode
        if (!pcode.isEmpty()) {
            cr.updatePostcodeById(pcode, customerId);
        }
        //update county
        if (!county.isEmpty()) {
            cr.updateCountyById(county, customerId);
        }
        //update email address
        if (!email.isEmpty()) {
            cr.updateEmailById(email, customerId);
        }
        //update phone number
        if (!phone.isEmpty()) {
            cr.updatePhoneById(phone, customerId);
        }
        return "edit_profile_consumer";
    }

    @GetMapping("/browse_bundles_consumer")
    public String browseBundlesConsumer(Model model) {
        //find all bundles that have not expired and have not already been reserved
        List<Bundle> allBundles = br.findByReservedAndExpired(false, false);
        ArrayList<Bundle> bundles = new ArrayList<>();
        //find if any bundles have gone past expiry
        for (Bundle bundle : allBundles) {
            if(bundle.getPickUpWindow() < LocalTime.now().getHour() || bundle.getTimeStamp().toLocalDate().isBefore(LocalDate.now())) {
                br.setBundleExpired(bundle.getPostingID());
                bundle.setExpired(true);
            }
            else{
                bundles.add(bundle);
            }
        }
        //add bundles to web page
        model.addAttribute("allBundles", bundles);
        return "browse_bundles_consumer";
    }

    @PostMapping("/browse_bundles_consumer")
    public String reserveBundleConsumer(@RequestParam("postingID") int postingID, Model model) {
        //get bundle from web page
        Optional<Bundle> b = br.findById(postingID);
        Bundle b1 = b.get();
        //get current user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = auth.getName();
        Customer customer = cr.findByDName(currentUsername).get(0);

        //generate claim code for reservation
        String claimCode = customer.generateClaimCode();
        //make and save reservation
        Reservation r = new Reservation(b1, customer, b1.getSeller(), LocalDateTime.now(), claimCode, false, false);
        rr.save(r);
        //update bundle to reserved
        br.setBundleReserved(true, b1.getPostingID());

        return "redirect:browse_bundles_consumer";
    }

    @GetMapping("/manage_bundles_consumer")
    public String listReservedBundles(Model model) {
        //get current user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = auth.getName();
        Customer c = cr.findByDName(currentUsername).get(0);
        //get all reservations the user has and list on webpage
        List<Reservation> reservations = rr.findByCustomerID(c.getCustomerID());
        if (!reservations.isEmpty()) {
            for(Reservation reservation : reservations){
                //set no show if past pickup window
                if(reservation.getCollected() == false && !reservation.getNoShow() && (reservation.getBundle().getPickUpWindow() < LocalTime.now().getHour() || reservation.getBundle().getTimeStamp().toLocalDate().isBefore(LocalDate.now()))){
                    rr.setReservationNoShow(true, reservation.getBundle().getPickUpWindow() + 1);
                    reservation.setNoShow(true);
                }
            }
        }
        model.addAttribute("reservations", reservations);
        return "manage_bundles_consumer";
    }

    @PostMapping("/report_issue_consumer")
    public String reportIssue(){
        return "report_issue_consumer";
    }

    @GetMapping("/view_analytics_consumer")
    public String viewAnalyticsConsumer(Model model) {
        //get current user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = auth.getName();
        Customer c = cr.findByDName(currentUsername).get(0);

        //get total number of collected bundles for the user
        //and list of all bundles collected
        int num_collected = 0;
        List<Reservation> collectedReservations = new ArrayList<Reservation>();
        List<Reservation> customerReservations = rr.findByCustomerID(c.getCustomerID());
        for (Reservation r : customerReservations) {
            if(r.getCollected() ){num_collected++; collectedReservations.add(r); }
        }

        //checks the user's streak
        if(!customerReservations.isEmpty() && c.getStreakLastUpdate()!=null) {
            //get current time stamp
            LocalDateTime now = LocalDateTime.now();
            //get timestamp of last time streak was updated
            LocalDateTime LastStreakUpdate = c.getStreakLastUpdate();
            //get the years as numbers of timestamps
            int year1 = LastStreakUpdate.getYear();
            int year2 = now.getYear();
            //get the week into the year as number for the timestamps
            int week1 = LastStreakUpdate.get(WeekFields.ISO.weekOfWeekBasedYear());
            int week2 = now.get(WeekFields.ISO.weekOfWeekBasedYear());

            //if last streak update was more than a week ago set streak 0
            if(week2-week1>1 && year1 == year2){
                c.setStreak(0);
                cr.updateStreakById(0, c.getCustomerID());
            }
            else if(year1==year2+1 && !(week1 == 52 && week2==1)){
                c.setStreak(0);
                cr.updateStreakById(0, c.getCustomerID());
            }
        }

        //gets number of categories and sellers purchased from
        //only if reservation was collected
        List<String> categories = new ArrayList<>();
        List<Seller> sellers = new ArrayList<>();
        for(Reservation r : collectedReservations){
            categories.add(r.getBundle().getCategory());
            sellers.add(r.getBundle().getSeller());
        }

        //gets unique categories
        List<String> uniqueCategories = new ArrayList<>();
        for(String category : categories){
            if(!uniqueCategories.contains(category)){uniqueCategories.add(category);}
        }
        //number of unique categories
        int num_categories = uniqueCategories.size();
        //gets unique sellers
        List<Seller> uniqueSellers = new ArrayList<>();
        for(Seller s : sellers){
            if(!uniqueSellers.contains(s)){uniqueSellers.add(s);}
        }
        //number of unique sellers
        int num_sellers = uniqueSellers.size();

        //gets amount of co2 saved
        //fish and meat: 1.5kg
        //bakery: 0.8kg
        //snacks:0.6kg
        //dairy: 1kg
        //fruit vegetables and legumes: 0.5kg
        //groceries: 1.2kg
        //other: 2kg
        double co2_saved = 0;
        for (Reservation collectedReservation : collectedReservations) {
            if (collectedReservation.getBundle().getCategory().equals("meats")) {
                co2_saved += (4.2 * 1.5);
            } else if (collectedReservation.getBundle().getCategory().equals("bakery")) {
                co2_saved += (4.2 * 0.8);
            } else if (collectedReservation.getBundle().getCategory().equals("snacks")) {
                co2_saved += (4.2 * 0.6);
            } else if (collectedReservation.getBundle().getCategory().equals("dairy")) {
                co2_saved += (4.2 * 1);
            } else if (collectedReservation.getBundle().getCategory().equals("plants")) {
                co2_saved += (4.2 * 0.5);
            } else if (collectedReservation.getBundle().getCategory().equals("groceries")) {
                co2_saved += (4.2 * 1.2);
            } else {
                co2_saved += (4.2 * 2);
            }
        }

        //badges array: [1 meal saved, 5 meals saved, 10 meals saved,
        //              1 category, 3 categories, 5 categories,
        //              1 seller, 5 sellers, 10 sellers,
        //              20 co2 saved, 50 co2 saved, 100 co2 saved]
        boolean[] badges = new boolean[12];

        //set badges for number of meals saved
        if(num_collected==0){ badges[0] = false; badges[1] = false; badges[2] = false;}
        else if(num_collected>=1 && num_collected<5){ badges[0] = true; badges[1] = false; badges[2] = false;}
        else if(num_collected>=5 && num_collected<10){ badges[0] = true; badges[1] = true; badges[2] = false;}
        else{ badges[0] = true; badges[1] = true; badges[2] = true;}

        //set badges for number of categories
        if(num_categories==0){ badges[3] = false; badges[4] = false; badges[5] = false;}
        else if(num_categories>=1 && num_categories<3){ badges[3] = true; badges[4] = false; badges[5] = false;}
        else if(num_categories>=3 && num_categories<5){ badges[3] = true; badges[4] = true; badges[5] = false;}
        else { badges[3] = true; badges[4] = true; badges[5] = true;}

        //set badges for seller purchased from
        if(num_sellers==0){ badges[6] = false; badges[7] = false; badges[8] = false;}
        else if(num_sellers>=1 && num_sellers<5){ badges[6] = true; badges[7] = false; badges[8] = false;}
        else if(num_sellers>=5 && num_sellers<10){ badges[6] = true; badges[7] = true; badges[8] = false;}
        else { badges[6] = true; badges[7] = true; badges[8] = true;}

        //sets badges for amount of co2 saved
        if(co2_saved<20){ badges[9] = false; badges[10] = false; badges[11] = false;}
        else if(co2_saved>=20 && co2_saved<50){ badges[9] = true; badges[10] = false; badges[11] = false;}
        else if(co2_saved>=50 && co2_saved<100){ badges[9] = true; badges[10] = true; badges[11] = false;}
        else { badges[9] = true; badges[10] = true; badges[11] = true;}

        //counts how many badges they have collected
        int no_badges = 0;
        for(int i = 0; i<badges.length; i++)
        {
            if(badges[i]) {
                no_badges++;
            }
        }

        //add analytics to web page
        model.addAttribute("streak", c.getStreak());
        model.addAttribute("collected", num_collected);
        model.addAttribute("co2_save", co2_saved);
        model.addAttribute("no_badges", no_badges);
        model.addAttribute("badges", badges);
        return "view_analytics_consumer";
    }
}
