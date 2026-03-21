package com.waste_manager.team_roadmap;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

@Component
public class CSVDatabase {

    @Autowired
    private ResourceLoader resourceLoader;
    private static final String DELIMITER = "&";

    @Autowired
    SellerRepository sellerRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    BundleRepository bundleRepository;
    @Autowired
    ReservationRepository reservationRepository;
    @Autowired
    IssueReportRepository issueReportRepository;

    File sellerCSV;
    File customerCSV;
    File bundleCSV;
    File reservationCSV;
    File issueCSV;

    private static List<String> getRecordFromLine(String line) {
        List<String> values = new ArrayList<String>();
        try (Scanner rowScanner = new Scanner(line)) {
            rowScanner.useDelimiter(DELIMITER);
            while (rowScanner.hasNext()) {
                values.add(rowScanner.next());
            }
        }
        return values;
    }

    private void setupFilePaths() throws IOException {
        String userHome = System.getProperty("user.home");


        String dataPath = userHome + File.separator + "TeamRoadmap" + File.separator + "FoodWasteApp";
        // make sure directory exists
        Files.createDirectories(Paths.get(dataPath));
        dataPath += File.separator;

        sellerCSV = new File(dataPath + "sellers.csv");
        customerCSV = new File(dataPath + "customers.csv");
        bundleCSV = new File(dataPath + "bundles.csv");
        reservationCSV = new File(dataPath + "reservation.csv");
        issueCSV = new File(dataPath + "issues.csv");
    }

    @PostConstruct
    public void readAllCVSFiles() throws IOException {
        // set file links
        setupFilePaths();

        // set up logging
        Log log = LogFactory.getLog(TeamRoadmapApplication.class);


        // load sellers
        log.info("Loading Sellers");

        Scanner scanner;
        boolean newSellerFile = sellerCSV.createNewFile();
        if (newSellerFile) { // if file new then we read from static
            scanner = new Scanner(new InputStreamReader(new ClassPathResource("static/csv/sellers.csv").getInputStream()));
        } else { // otherwise read that file
            scanner = new Scanner(sellerCSV);
        }

        while (scanner.hasNextLine()) {
            List<String> seller_info = getRecordFromLine(scanner.nextLine());

            // add to repo
            sellerRepository.save(new Seller(
                seller_info.get(0),
                    seller_info.get(1),
                    seller_info.get(2),
                    seller_info.get(3),
                    seller_info.get(4),
                    seller_info.get(5),
                    seller_info.get(6),
                    seller_info.get(7),
                    seller_info.get(8),
                    newSellerFile
            ));
        }
        log.info("Sellers Loaded");

        // load customers
        log.info("Loading Customers");
        boolean newCustomerFile = customerCSV.createNewFile();
        if (newCustomerFile) {
            scanner = new Scanner(new InputStreamReader(new ClassPathResource("static/csv/customers.csv").getInputStream()));
        }else{
            scanner = new Scanner(customerCSV);
        }

        while (scanner.hasNextLine()) {
            List<String> customer_info = getRecordFromLine(scanner.nextLine());

            // convert from text to array lists
            String sanitised_badges = customer_info.get(10).replaceAll("[\\s\\[\\]']", "");
            ArrayList<Boolean> badges = new ArrayList<>();
            for (String badge : sanitised_badges.split(",")) {
                badges.add(Boolean.parseBoolean(badge));
            }
            // add to repo
            customerRepository.save(new Customer(
                    customer_info.get(0),
                    customer_info.get(1),
                    customer_info.get(2),
                    customer_info.get(3),
                    customer_info.get(4),
                    customer_info.get(5),
                    customer_info.get(6),
                    customer_info.get(7),
                    customer_info.get(8),
                    Integer.parseInt(customer_info.get(9)),
                    badges,
                    newCustomerFile
            ));

        }
        log.info("Customers Loaded");

        // load bundles
        log.info("Loading Bundles");

        if(bundleCSV.createNewFile()) {
            scanner = new Scanner(new InputStreamReader(new ClassPathResource("static/csv/bundles.csv").getInputStream()));
        }else{
            scanner = new Scanner(bundleCSV);
        }
        while (scanner.hasNextLine()) {
            List<String> bundle_info = getRecordFromLine(scanner.nextLine());


            // convert from text to array lists
            String sanitised_contents = bundle_info.get(2).replaceAll("[\\s\\[\\]']", "");
            String sanitised_allergens = bundle_info.get(3).replaceAll("[\\s\\[\\]']", "");

            ArrayList<String> contents = new ArrayList<>(Arrays.asList(sanitised_contents.split(",")));
            ArrayList<String> allergens = new ArrayList<>(Arrays.asList(sanitised_allergens.split(",")));

            // get relational components
            Seller seller = sellerRepository.findByID(Integer.parseInt(bundle_info.get(0))).get();

            // add to repo
            bundleRepository.save(new Bundle(
                    seller,
                    bundle_info.get(1),
                    contents,
                    allergens,
                    LocalDateTime.parse(bundle_info.get(4)),
                    Float.parseFloat(bundle_info.get(5)),
                    Integer.parseInt(bundle_info.get(6)),
                    Integer.parseInt(bundle_info.get(7)),
                    Boolean.parseBoolean(bundle_info.get(8)),
                    Boolean.parseBoolean(bundle_info.get(9)),
                    bundle_info.get(10) // weather flag
            ));

        }
        log.info("Bundles Loaded");

        // load reservations
        log.info("Loading Reservations");

        if(reservationCSV.createNewFile()) {
            scanner = new Scanner(new InputStreamReader(new ClassPathResource("static/csv/reservation.csv").getInputStream()));
        }else{
            scanner = new Scanner(reservationCSV);
        }
        while (scanner.hasNextLine()) {
            List<String> reservation_info = getRecordFromLine(scanner.nextLine());

            // get relational components
            Bundle bundle = bundleRepository.findById(Integer.parseInt(reservation_info.get(0))).get();
            Customer customer = customerRepository.findByID(Integer.parseInt(reservation_info.get(1))).get();
            Seller seller = sellerRepository.findByID(Integer.parseInt(reservation_info.get(2))).get();

            // add to repo
            reservationRepository.save(new Reservation(
                    bundle,
                    customer,
                    seller,
                    LocalDateTime.parse(reservation_info.get(3)),
                    reservation_info.get(4),
                    Boolean.parseBoolean(reservation_info.get(5)),
                    Boolean.parseBoolean(reservation_info.get(6))
            ));
        }

        log.info("Reservations Loaded");

        // load issue reports
        log.info("Loading Issue Reports");

        if(issueCSV.createNewFile()) {
            scanner = new Scanner(new InputStreamReader(new ClassPathResource("static/csv/issues.csv").getInputStream()));
        }else{
            scanner = new Scanner(issueCSV);
        }
        while (scanner.hasNextLine()) {
            List<String> issue_info = getRecordFromLine(scanner.nextLine());

            // get relational components
            Bundle bundle = bundleRepository.findById(Integer.parseInt(issue_info.get(0))).get();
            Customer customer = customerRepository.findByID(Integer.parseInt(issue_info.get(1))).get();

            // add to repo
            issueReportRepository.save(new IssueReport(
                    bundle,
                    customer,
                    issue_info.get(1),
                    issue_info.get(2),
                    Boolean.parseBoolean((issue_info.get(3))),
                    issue_info.get(4)
            ));
        }

        log.info("Issue Reports Loaded");

    }


    @PreDestroy
    public void writeAllCSVFiles() throws IOException {
        // set up logging
        Log log = LogFactory.getLog(TeamRoadmapApplication.class);

        // get all file writers
        PrintWriter seller_writer = new PrintWriter(new FileOutputStream(sellerCSV));
        PrintWriter customer_writer = new PrintWriter(new FileOutputStream(customerCSV));
        PrintWriter bundle_writer = new PrintWriter(new FileOutputStream(bundleCSV));
        PrintWriter reservation_writer = new PrintWriter(new FileOutputStream(reservationCSV));
        PrintWriter issue_writer = new PrintWriter(new FileOutputStream(issueCSV));

        // write all sellers
        log.info("writing sellers");
        for (Seller seller : sellerRepository.findAll()) {
            seller_writer.printf(
                    "%s&%s&%s&%s&%s&%s&%s&%s&%s\n",
                    seller.getfName(),
                    seller.getsName(),
                    seller.getdName(),
                    seller.getAddress(),
                    seller.getPostcode(),
                    seller.getCounty(),
                    seller.getEmail(),
                    seller.getPhone(),
                    seller.getPassword()
                );
        }
        log.info("sellers written");
        seller_writer.close();


        // write all customers
        log.info("writing customers");
        for (Customer customer : customerRepository.findAll()) {
            customer_writer.printf(
                    "%s&%s&%s&%s&%s&%s&%s&%s&%s&%d&%s\n",
                    customer.getfName(),
                    customer.getsName(),
                    customer.getdName(),
                    customer.getAddress(),
                    customer.getPostcode(),
                    customer.getCounty(),
                    customer.getEmail(),
                    customer.getPhone(),
                    customer.getPassword(),
                    customer.getStreak(),
                    customer.getBadges().toString()
            );

        }
        log.info("customers written");
        customer_writer.close();


        // write all bundles
        log.info("writing bundles");
        for (Bundle bundle : bundleRepository.findAll()) {
            bundle_writer.printf(
                    "%d&%s&%s&%s&%s&%f&%d&%d&%b&%b&%s\n",
                    bundle.getSeller().getSellerID(),
                    bundle.getCategory(),
                    bundle.getContents().toString(),
                    bundle.getAllergens().toString(),
                    bundle.getTimeStamp().toString(),
                    bundle.getPrice(),
                    bundle.getDiscount(),
                    bundle.getPickUpWindow(),
                    bundle.getReserved(),
                    bundle.getExpired(),
                    bundle.getWeatherFlag()
            );
        }
        bundle_writer.close();
        log.info("bundles written");


        // write all reservations
        log.info("writing reservations");
        for (Reservation reservation : reservationRepository.findAll()) {
            reservation_writer.printf(
                    "%d&%d&%d&%s&%s&%b&%b\n",
                    reservation.getBundle().getPostingID(),
                    reservation.getCustomer().getCustomerID(),
                    reservation.getSeller().getSellerID(),
                    reservation.getTimeStamp().toString(),
                    reservation.getClaimCode(),
                    reservation.getNoShow(),
                    reservation.getCollected()
            );
        }
        reservation_writer.close();
        log.info("reservations written");

        // write all reservations
        log.info("writing issue reports");
        for (IssueReport issue : issueReportRepository.findAll()) {
            issue_writer.printf(
                    "%d&%d&%s&%s&%b&%s\n",
                    issue.getBundle().getPostingID(),
                    issue.getCustomer().getCustomerID(),
                    issue.getType(),
                    issue.getDescription(),
                    issue.getResolved(),
                    issue.getSellerResponse()
            );
        }
        issue_writer.close();
        log.info("issue reports written");



        log.info("finished writing database to csv files");
    }
}
