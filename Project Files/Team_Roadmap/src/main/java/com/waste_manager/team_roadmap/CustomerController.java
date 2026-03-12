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
import java.util.List;
import java.util.Objects;
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
            cr.save(c1);
            System.out.println("sign up successful");
            return "sign_in";
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
            cr.updatePasswordById(pwd1, customerId);
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
    public String browseBundlesConsumer(Model model, @RequestParam(value="category", required = false) String category,
                                        @RequestParam(value = "postcode", required = false) String postcode,
                                        @RequestParam(value = "price_selector", required = false) String priceselector,
                                        @RequestParam(value="price", required = false) String price,
                                        @RequestParam(value = "time_selector", required = false) String timeSelector,
                                        @RequestParam(value = "time", required = false) String time,
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
                                        @RequestParam(value="nuts", required = false) String nuts) {


        //find all bundles that have not expired and have not already been reserved
        List<Bundle> allBundles = br.findByReservedAndExpired(false, false);
        ArrayList<Bundle> bundles = new ArrayList<>();
        //find if any bundles have gone past expiry;
        for (Bundle bundle : allBundles) {
            if (bundle.getPickUpWindow() < LocalTime.now().getHour() || bundle.getTimeStamp().toLocalDate().isBefore(LocalDate.now())) {
                br.setBundleExpired(bundle.getPostingID());
                bundle.setExpired(true);
            // Check filters
            } else if ((category == null || category.equals("Unselected") || bundle.getCategory().equals(category)) &&
                (postcode == null || Objects.equals(postcode, "") || bundle.getSeller().getPostcode().equals(postcode)) &&
                (celery == null || !bundle.getAllergens().contains(celery)) &&
                (gluten == null || !bundle.getAllergens().contains(gluten)) &&
                (crustaceans == null || !bundle.getAllergens().contains(crustaceans)) &&
                (eggs == null || !bundle.getAllergens().contains(eggs)) &&
                (fish == null || !bundle.getAllergens().contains(fish)) &&
                (lupin == null || !bundle.getAllergens().contains(lupin)) &&
                (milk == null || !bundle.getAllergens().contains(milk)) &&
                (molluscs == null || !bundle.getAllergens().contains(molluscs)) &&
                (mustard == null || !bundle.getAllergens().contains(mustard)) &&
                (peanuts == null || !bundle.getAllergens().contains(peanuts)) &&
                (sesame == null || !bundle.getAllergens().contains(sesame)) &&
                (soybeans == null || !bundle.getAllergens().contains(soybeans)) &&
                (sulphur == null || !bundle.getAllergens().contains(sulphur)) &&
                (nuts == null || !bundle.getAllergens().contains(nuts)) &&
                ((priceselector == null || price == null || Objects.equals(price, "") ||
                    (priceselector.equals("more") && Float.parseFloat(price) < bundle.getPrice()) ||
                    (priceselector.equals("equals") && Float.parseFloat(price) == bundle.getPrice()) ||
                    (priceselector.equals("less") && Float.parseFloat(price) > bundle.getPrice()))) &&
                (time == null ||
                    (timeSelector.equals("more") && Integer.parseInt(time.substring(0, 2)) < bundle.getPickUpWindow()) ||
                    (timeSelector.equals("less") && Integer.parseInt(time.substring(0, 2)) > bundle.getPickUpWindow()) ||
                    (timeSelector.equals("equal") && Integer.parseInt(time.substring(0, 2)) == bundle.getPickUpWindow())))
                {
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
        Reservation r = new Reservation(b1, customer, b1.getSeller(), LocalDateTime.now(), claimCode, false, false, "someWeather");
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
    public String viewAnanalyticsConsumer(Model model) {
        //get current user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = auth.getName();
        Customer c = cr.findByDName(currentUsername).get(0);

        //get total number of collected bundles for the user
        int num_collected = 0;
        List<Reservation> customerReservations = rr.findByCustomerID(c.getCustomerID());
        for (Reservation r : customerReservations) {
            if(r.getCollected() ){num_collected++;}
        }


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
        //add analytics to web page
        model.addAttribute("streak", c.getStreak());
        model.addAttribute("collected", num_collected);
        model.addAttribute("co2_save", 4.2*num_collected);
        model.addAttribute("no_badges", 0);
        return "view_analytics_consumer";
    }




}
