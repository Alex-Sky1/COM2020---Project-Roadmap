package com.waste_manager.team_roadmap;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.cglib.core.Local;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.WeekFields;
import java.util.*;
import java.time.LocalDateTime;

@Controller
public class SellerController {
    private final SellerRepository sr;
    private final CustomerRepository cr;
    private final BundleRepository br;
    private final ReservationRepository rr;
    private final IssueReportRepository irr;
    private final AdminRepository ar;
    private final CustomUserDetailService cuds;

    public SellerController(SellerRepository sellerRepository, CustomerRepository customerRepository, BundleRepository bundleRepository, ReservationRepository reservationRepository, IssueReportRepository issueReportRepository, AdminRepository adminRepository, CustomUserDetailService customUserDetailService) {
        this.sr = sellerRepository;
        this.cr = customerRepository;
        this.br = bundleRepository;
        this.rr = reservationRepository;
        this.irr = issueReportRepository;
        this.ar = adminRepository;
        this.cuds = customUserDetailService;
    }

    @PostMapping("/sign_up_seller")
    public String signup(@RequestParam("fname") String fname, @RequestParam("sname") String sname,
                         @RequestParam("business") String business, @RequestParam("address_line_1") String al1,
                         @RequestParam("postcode") String pcode, @RequestParam("county") String county,
                         @RequestParam("email") String email, @RequestParam("phone") String phone,
                         @RequestParam("password1") String pwd1, @RequestParam("password2") String pwd2,
                         @RequestParam(value = "accept", required = false) String tosAccept, Model model) {


        List<Seller> s = sr.findBydName(business);
        List<Customer> c = cr.findBydName(business);
        Optional<Admin> a = ar.findBydName(business);
        // Check that passwords match
        if(!pwd1.equals(pwd2)){
            model.addAttribute("error", "Passwords don't match");
        }

        // If any field is empty don't allow sign up
        else if(fname.isEmpty() || sname.isEmpty() || business.isEmpty() || al1.isEmpty() ||  pcode.isEmpty() || county.isEmpty() || email.isEmpty() || phone.isEmpty() || pwd1.isEmpty()){
            model.addAttribute("error", "Please fill in all the fields");
        }

        // Check that no other seller or customer or admin is using that username
        else if (!s.isEmpty() || !c.isEmpty() || a.isPresent()) {
            model.addAttribute("error", "Username already exists");
        }
        //check if they accepted terms and conditions
        else if(tosAccept==null) {
            model.addAttribute("error", "Please accept the terms and conditions");
        }else {
            //create and save new seller
            Seller s1 = new Seller(fname, sname, business, al1, pcode, county, email, phone, pwd1, true);
            //check if password fits requirements
            if(!s1.validatePassword(pwd1)) {
                model.addAttribute("error", "Invalid password");
            }
            else {
                sr.save(s1);
                return "sign_in";
            }
        }
        return "sign_up_seller";
    }

    @PostMapping("/post_bundle_seller")
    public String postBundle(@RequestParam("category") String category, @RequestParam("price") String price,
                             @RequestParam("pickup") String pickup, @RequestParam("bundle_numbers") String quantity,
                             @RequestParam("discount") String discount, @RequestParam("hidden_items" )String contents,
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

        // Get current logged in user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Seller s = getSellerProfile(auth);

        String[] WeatherFlags = {"Sunny", "Rainy", "Cloudy"};
        Random rand = new Random();
        String weatherFlag = WeatherFlags[rand.nextInt(0,3)];

        // Check if any allergens have been selected
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

        // Add all contents to list
        ArrayList<String> content = new ArrayList<String>(Arrays.asList(contents.split(",")));

        // Take pickup hour using first hour as just an integer
        int pickupHr = Integer.parseInt(pickup.substring(0,2));

        // Make quantity amount of bundles and save to database
        for (int i = 0; i < Integer.parseInt(quantity); i++) {

            Bundle bundle = new Bundle
                    (s, category, content, allergens, LocalDateTime.now(), Float.parseFloat(price),
                    Integer.parseInt(discount), pickupHr, false, false, weatherFlag);
            br.save(bundle);
        }
        return "post_bundle_seller";
    }


    @PostMapping("edit_profile_seller")
    public String editProfileSeller(@RequestParam(value = "fname", required = false) String fname, @RequestParam(value = "sname", required = false) String sname,
                                    @RequestParam(value = "business", required = false) String business, @RequestParam(value = "address_line_1", required = false) String al1,
                                    @RequestParam(value = "postcode", required = false) String pcode, @RequestParam(value = "county", required = false) String county,
                                    @RequestParam(value = "email", required = false) String email, @RequestParam(value = "phone", required = false) String phone,
                                    @RequestParam(value = "password1", required = false) String pwd1, @RequestParam(value = "password2", required = false) String pwd2,
                                    Model model, HttpServletRequest request) {

        List<Seller> s = sr.findBydName(business);
        List<Customer> c = cr.findBydName(business);
        Optional<Admin> a = ar.findBydName(business);

        // Get current user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Seller seller = getSellerProfile(auth);

        int sellerId= seller.getSellerID();

        // Check if business (username) field is not empty and not being used by anyone else
        if(!business.isEmpty()){
            if (!s.isEmpty() || !c.isEmpty() || a.isPresent()) {
                model.addAttribute("error", "Username already exists");
            } else {
                seller.setdName(business);
                sr.save(seller);

                //update authorization detail and add to session
                UserDetails newUser = cuds.loadUserByUsername(business);
                UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(newUser, newUser.getPassword(), newUser.getAuthorities());
                token.setDetails(auth.getDetails());
                SecurityContextHolder.getContext().setAuthentication(token);

                request.getSession(false).setAttribute(HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY, SecurityContextHolder.getContext());
            }
        }

        // Check passwords match if password is being changed and is a valid password
        if(!pwd1.isEmpty() && pwd1.equals(pwd2)){
            if(!seller.validatePassword(pwd1)) {
                model.addAttribute("error", "Invalid password");
            }else {
                seller.setPassword(pwd1);
            }
        }else if (!pwd1.equals(pwd2)){
            model.addAttribute("error", "Passwords don't match");
        }
        // Update first name
        if(!fname.isEmpty()){
            seller.setfName(fname);
        }
        // Update surname
        if(!sname.isEmpty()){
            seller.setsName(sname);
        }
        // Update address
        if(!al1.isEmpty()){
            seller.setAddress(al1);
        }
        // Update postcode
        if(!pcode.isEmpty()){
            seller.setPostcode(pcode);
        }
        // Update county
        if(!county.isEmpty()){
            seller.setCounty(county);
        }
        // Update email address
        if(!email.isEmpty()){
            seller.setEmail(email);
        }
        // Update phone number
        if(!phone.isEmpty()){
            seller.setPhone(phone);
        }

        sr.save(seller);

        return "edit_profile_seller";
    }


    @GetMapping("/manage_bundles_seller")
    public String manageBundlesSeller(Model model) {

        // Get current logged in user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Seller s = getSellerProfile(auth);

        // Add all reservations to web page
        List<Reservation> reservations = rr.findBySellerID(s.getSellerID());
        model.addAttribute("reservations", reservations);

        // Add all bundles that are not in reservation to web page
        List<Bundle> allBundles = br.findBySellerID(s.getSellerID());
        ArrayList<Bundle> bundles = new ArrayList<>();

        for(Bundle bundle : allBundles){
            if(!bundle.getReserved()) {
                // Set expired if pickup window has passed
                if(!bundle.getExpired() && (bundle.getPickUpWindow() < LocalTime.now().getHour() || bundle.getTimeStamp().toLocalDate().isBefore(LocalDate.now()))){
                    br.setBundleExpired(bundle.getPostingID());
                    bundle.setExpired(true);
                }
                bundles.add(bundle);
            }
            else{
                List<Reservation> reserves = rr.findByBundleID(bundle.getPostingID());
                // Set no show if pickup window has passed
                if(reserves.get(0).getCollected() == false && !reserves.get(0).getNoShow() && (bundle.getPickUpWindow() < LocalTime.now().getHour() || reserves.get(0).getBundle().getTimeStamp().toLocalDate().isBefore(LocalDate.now())) ){
                    rr.setReservationNoShow(true, reserves.get(0).getReservationID());
                }

            }
        }
        model.addAttribute("bundles", bundles);

        return "manage_bundles_seller";
    }

    @PostMapping("/manage_bundles_seller")
    public String deleteBundle(@RequestParam(value = "PostingID") String postingID){
        br.deleteBundleByID(Integer.parseInt(postingID));
        return "redirect:/manage_bundles_seller";
    }

    @GetMapping("/manage_reservations_seller")
    public String manage_reservations_seller(Model model) {
        // Get current user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Seller s = getSellerProfile(auth);

        // Add all reservations to web page
        List<Reservation> allReservations = rr.findBySellerID(s.getSellerID());
        ArrayList<Reservation> reservations = new ArrayList<>();

        for (Reservation reservation : allReservations) {
            // Set no show if pickup window passed
            if(reservation.getCollected() == false && !reservation.getNoShow() && (reservation.getBundle().getPickUpWindow() < LocalTime.now().getHour() || reservation.getBundle().getTimeStamp().toLocalDate().isBefore(LocalDate.now()))) {
                rr.setReservationNoShow(true, reservation.getReservationID());
            }
            // Only add reservations that have not been collected and not classed as no show
            if(!reservation.getCollected() && !reservation.getNoShow()){
                reservations.add(reservation);
            }
        }
        model.addAttribute("reservations", reservations);
        return "manage_reservations_seller";
    }

    @PostMapping("manage_reservations_seller")
    public String manageReservationsSeller(@RequestParam("ClaimCode") String claimCode, @RequestParam("reservationID") long reservationID, Model model){

        // Get reservation that a claim code has been entered for
        Reservation res = rr.findById(reservationID).get();

        // Check if claim code is correct
        boolean equalClaimCode = claimCode.equals(res.getClaimCode());

        // Add true/false for if it was correct
        model.addAttribute("success", equalClaimCode);

        // Change collected status in database
        rr.setReservationCollected(equalClaimCode, reservationID);

        Customer c = res.getCustomer();
        LocalDateTime now = LocalDateTime.now();

        if(c.getStreakLastUpdate() == null){

            c.setStreakLastUpdate(now);
            c.setStreak(1);
            cr.updateStreakById(1, c.getCustomerID());
            cr.updateStreakUpdateTimeByID(now, c.getCustomerID());
        } else {

            // Update streak
            LocalDateTime LastStreakUpdate = c.getStreakLastUpdate();
            int year1 = now.getYear();
            int year2 = LastStreakUpdate.getYear();
            int week1 = now.get(WeekFields.ISO.weekOfWeekBasedYear());
            int week2 = LastStreakUpdate.get(WeekFields.ISO.weekOfWeekBasedYear());

            if (week2 - week1 > 1 && year1 == year2) {
                c.setStreak(0);
                cr.updateStreakById(0, c.getCustomerID());
            }

            if (week2 - week1 == 1 && year1 == year2) {
                c.setStreak(c.getStreak() + 1);
                cr.updateStreakById(c.getStreak(), c.getCustomerID());
            }
        }

        // Refresh page
        return "redirect:/manage_reservations_seller";
    }

    @GetMapping("/edit_bundle_seller")
    public String edit_bundle_seller(@RequestParam("PostingID") String ID, Model model){
        //get bundle and add to web page to fill in current details
        Bundle bundle = br.findById(Long.parseLong(ID)).get();
        model.addAttribute("bundle", bundle);
        return "/edit_bundle_seller";
    }
    @PostMapping("/edit_bundle_seller")
    public String editBundle(@RequestParam("PostingID") String ID,
                             @RequestParam("category") String category, @RequestParam("price") String price,
                             @RequestParam("pickup") String pickup, @RequestParam("discount") String discount,
                             @RequestParam("hidden_items")String contents,
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

        Bundle bundle = br.findById(Integer.parseInt(ID)).get();
        //set bundle changes from web page
        bundle.setCategory(category);
        bundle.setPrice(Float.parseFloat(price));
        bundle.setContents(new ArrayList<>(Arrays.asList(contents.split(","))));
        bundle.setPickUpWindow(Integer.parseInt(pickup.substring(0,2)));
        bundle.setDiscount(Integer.parseInt(discount));
        bundle.setExpired(false);

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
        bundle.setAllergens(allergens);

        br.save(bundle);

        return "redirect:/manage_bundles_seller";
    }

    @GetMapping("/forecasting_seller")
    public String forecasting_seller(Model model) throws Exception {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Seller s = getSellerProfile(auth);

        List<Bundle> allBundles = br.findBySellerID(s.getSellerID());
        List<Reservation>  allReservations = rr.findBySellerID(s.getSellerID());
        ArrayList<Bundle> bundles = new ArrayList<>();
        ArrayList<Double> probNoShow = new ArrayList<>();

        for( Bundle bundle : allBundles ) {
            if(bundle.getTimeStamp().getDayOfYear() == LocalDate.now().getDayOfYear() && bundle.getTimeStamp().getYear() == LocalDate.now().getYear()) {
                Forecast forecast = new Forecast(LocalDateTime.now(), s.getSellerID(), bundle.getWeatherFlag(), bundle.getCategory(), new ArrayList<>(allBundles), new ArrayList<>(allReservations));
                bundles.add(bundle);
                probNoShow.add(forecast.prediction(bundle, "noshow"));
            }
        }

        model.addAttribute("bundles", bundles);
        model.addAttribute("probNoShow", probNoShow.toArray());
        model.addAttribute("Confidence", "Confidence: Some Confidence");
        model.addAttribute("Rationale", "Rationale: Some Rationale");
        model.addAttribute("Recommendation", "Some Recommendation");
        return "forecasting_seller";
    }

    @PostMapping("/forecasting_seller")
    public String forecastingSeller(){
        return "forecasting_seller";
    }

    @GetMapping("view_analytics_seller")
    public String viewAnalyticsSeller(Model model){

        // Get current user
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Seller s = getSellerProfile(auth);

        // Calculate no show : collected : expired
        int noShow = 0;
        int collected = 0;
        int expired = 0;
        List<Reservation> allReservations = rr.findBySellerID(s.getSellerID());

        for (Reservation reservation : allReservations) {
            if(reservation.getNoShow()){
                noShow++;
            }
            else if(reservation.getCollected()){
                collected++;
            }
            else{
                if(reservation.getBundle().getPickUpWindow() < LocalTime.now().getHour() || reservation.getBundle().getTimeStamp().toLocalDate().isBefore(LocalDate.now())) {
                    rr.setReservationNoShow(true, reservation.getReservationID());
                    reservation.setNoShow(true);
                    noShow++;
                }
            }
        }

        List<Bundle> allBundles = br.findBySellerID(s.getSellerID());
        for (Bundle bundle : allBundles) {
            if(bundle.getExpired()){
                expired++;
            }
            else if (!bundle.getReserved() && (bundle.getPickUpWindow() < LocalTime.now().getHour() || bundle.getTimeStamp().toLocalDate().isBefore(LocalDate.now()))) {
                br.setBundleExpired(bundle.getPostingID());
                bundle.setExpired(true);
                expired++;
            }
        }
        String sellThrough = collected + " : " + expired + " : " + noShow;
        model.addAttribute("sellThrough", sellThrough);

        //get all reservations made of seller bundles
        List<Reservation> sellerReservations = rr.findBySellerID(s.getSellerID());
        // Calculate waste proxy
        double waste_proxy = 0;
        for(Reservation reservation : sellerReservations) {
            if(reservation.getCollected()){
                if(reservation.getBundle().getCategory().equals("meats")) {
                    waste_proxy += (4.2 * 1.5);
                } else if (reservation.getBundle().getCategory().equals("bakery")) {
                    waste_proxy += (4.2 * 0.8);
                } else if (reservation.getBundle().getCategory().equals("snacks")) {
                    waste_proxy += (4.2 * 0.6);
                } else if (reservation.getBundle().getCategory().equals("dairy")) {
                    waste_proxy += (4.2 * 1);
                } else if (reservation.getBundle().getCategory().equals("plants")) {
                    waste_proxy += (4.2 * 0.5);
                } else if (reservation.getBundle().getCategory().equals("groceries")) {
                    waste_proxy += (4.2 * 1.2);
                } else {
                    waste_proxy += (4.2 * 2);
                }
            }
        }
        model.addAttribute("wasteProxy", waste_proxy);

        //calculate pricing effectiveness
        model.addAttribute("pricingEffectiveness", "-");

        //calculate operational insights
        model.addAttribute("operationalInsights", "-");

        return "view_analytics_seller";
    }

    @GetMapping("/manage_issues_seller")
    public String manageIssuesSeller(Model model)
    {
        // Get current logged in seller
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Seller s = getSellerProfile(auth);

        //find all issue reports
        List<IssueReport> allIssueReports = irr.findAll();

        //find all bundles from seller
        List<Bundle> allSellerBundles = br.findBySellerID(s.getSellerID());

        //find all issue reports that are linked to that seller
        //based on whether their bundle is linked to an issue report
        ArrayList<IssueReport> allSellerIssueReports = new ArrayList<>();
        for(int i = 0; i<allIssueReports.size(); i++)
        {
            for(int j = 0; j<allSellerBundles.size();j++)
            {
                if(allIssueReports.get(i).getBundle() == allSellerBundles.get(j))
                {
                    allSellerIssueReports.add(allIssueReports.get(i));
                }
            }
        }
        //List of unresolved issues
        ArrayList<IssueReport> unresolvedIssueReports = new ArrayList<>();
        ArrayList<IssueReport> resolvedIssueReports = new ArrayList<>();
        for(IssueReport issueReport : allSellerIssueReports) {
            if(!issueReport.getResolved()) {
                unresolvedIssueReports.add(issueReport);
            }
            else {
                resolvedIssueReports.add(issueReport);
            }
        }
        //add all issue reports to the web page
        model.addAttribute("unresolvedIssueReports", unresolvedIssueReports);
        model.addAttribute("resolvedIssueReports", resolvedIssueReports);
        return "manage_issues_seller";
    }

    @PostMapping("/manage_issues_seller")
    public String manageIssuesSeller(@RequestParam("sellerResponse") String sellerResponse,
                                     @RequestParam("issueID") int issueID){
        // Get current logged in seller
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Seller s = getSellerProfile(auth);

        //find issue report from repository
        Optional<IssueReport> issueReport = irr.findById(issueID);
        IssueReport issueReport1 = issueReport.get();
        //save new details for issue report
        issueReport1.setSellerResponse(sellerResponse);
        issueReport1.setResolved(true);
        //save them into the repository
        irr.save(issueReport1);
        return "manage_issues_seller";
    }


    public Seller getSellerProfile(Authentication auth){
        String currentUsername = auth.getName();
        if(auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))) {
            return ar.getAdmin().getSellerView();
        }else {
            return sr.findBydName(currentUsername).get(0);
        }
    }
}
