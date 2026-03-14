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
import java.util.Optional;


@Controller
public class CustomerController {

    private final CustomerRepository cr;
    private final SellerRepository sr;
    private final BundleRepository br;
    private final ReservationRepository rr;
    private final IssueReportRepository irr;

    public CustomerController(CustomerRepository customerRepository, SellerRepository sellerRepository, BundleRepository bundleRepository, ReservationRepository reservationRepository, IssueReportRepository issueReportRepository) {
        this.cr = customerRepository;
        this.sr = sellerRepository;
        this.br = bundleRepository;
        this.rr = reservationRepository;
        this.irr = issueReportRepository;
    }

    @PostMapping("/sign_up_consumer")
    public String signup(@RequestParam("fname") String fname, @RequestParam("sname") String sname,
                         @RequestParam("dname") String dname, @RequestParam("address_line_1") String al1,
                         @RequestParam("postcode") String pcode, @RequestParam("county") String county,
                         @RequestParam("email") String email, @RequestParam("phone") String phone,
                         @RequestParam("password1") String pwd1, @RequestParam("password2") String pwd2,
                         @RequestParam(value = "accept", required = false) String tosAccept, Model model) {

        List<Customer> c = cr.findByDName(dname);
        List<Seller> s = sr.findByDName(dname);
        //check Passwords match
        if(!pwd1.equals(pwd2)){
            System.out.println("passwords don't match");
            model.addAttribute("error", "Passwords don't match");
        }
        //if any field is empty don't allow sign up
        else if(fname.isEmpty() || sname.isEmpty() || dname.isEmpty() || al1.isEmpty() ||  pcode.isEmpty() || county.isEmpty() || email.isEmpty() || phone.isEmpty() || pwd1.isEmpty()){
            System.out.println("Please fill in all the fields");
            model.addAttribute("error", "Please fill in all the fields");
        }
        //check that no other seller or customer is using that username
        else if (!s.isEmpty() || !c.isEmpty()) {
            System.out.println("user name already exists");
            model.addAttribute("error", "Username already exists");
        }
        //check they have accepted the terms and conditions
        else if(tosAccept==null){
            System.out.println("please accept the terms and conditions");
            model.addAttribute("error", "Please accept the terms and conditions");

        }else {
            //create and save new customer
            Customer c1 = new Customer(fname, sname, dname, al1, pcode, county, email, phone, pwd1, 0, new ArrayList<Boolean>(), true);
            if(!c1.validateEmail(email)){
                model.addAttribute("error", "Invalid email");
            }
            else if(!c1.validatePassword(pwd1)) {
                model.addAttribute("error", "Invalid password");
            }
            else {
                cr.save(c1);
                System.out.println("sign up successful");
                return "sign_in";
            }
        }
        return "sign_up_consumer";
    }

    @PostMapping("/edit_profile_consumer")
    public String editProfileCustomer(@RequestParam(value = "fname", required = false) String fname, @RequestParam(value = "sname", required = false) String sname,
                                    @RequestParam(value = "dname", required = false) String dname, @RequestParam(value = "address_line_1", required = false) String al1,
                                    @RequestParam(value = "postcode", required = false) String pcode, @RequestParam(value = "county", required = false) String county,
                                    @RequestParam(value = "email", required = false) String email, @RequestParam(value = "phone", required = false) String phone,
                                    @RequestParam(value = "password1", required = false) String pwd1, @RequestParam(value = "password2", required = false) String pwd2,
                                      Model model) {

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
                model.addAttribute("error", "User name already exists");
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

    @GetMapping("/view_analytics_consumer")
    public String viewAnalyticsConsumer(Model model) {
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

    //when customer selects 'view issues' button on dashboard
    @GetMapping("/view_issues_consumer")
    public String viewIssuesConsumer(Model model) {
        //get current user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = auth.getName();
        Customer c = cr.findByDName(currentUsername).get(0);

        //find all issues that have the correct customer id
        List<IssueReport> allIssueReports = irr.findByCustomerID(c.getCustomerID());

        //add issue reports to the web page
        model.addAttribute("allIssueReports", allIssueReports);
        return "view_issues_consumer";
    }

    @GetMapping("/report_issue_consumer")
    public String openReportIssuePageConsumer(@RequestParam("reservationID") int reservationID, Model model) {
        //get bundle from web page
        Optional<Reservation> reservation = rr.findById(reservationID);
        Reservation res = reservation.get();

        model.addAttribute("reservation", res);
        model.addAttribute("reservationID", reservationID);

        return "report_issue_consumer";
    }

    @PostMapping("/report_issue_consumer")
    public String reportIssue(@RequestParam("reservationID") int reservationID,
                              @RequestParam("type") String type,
                              @RequestParam("description") String description)
    {
        //get current user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = auth.getName();
        Customer c = cr.findByDName(currentUsername).get(0);

        //get bundle from web page
        Reservation reservation = rr.findById(reservationID).get();
        Bundle bundle = reservation.getBundle();

        //save report to database
        IssueReport issueReport = new IssueReport(bundle, c, type, description, false, null);
        irr.save(issueReport);
        return "redirect:manage_bundles_consumer";
    }
}
