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

    ClassPathResource sellerCSV;
    ClassPathResource customerCSV;
    ClassPathResource bundleCSV;
    ClassPathResource reservationCSV;

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

    @PostConstruct
    public void read_all_csvs() throws IOException {
        // set up logging
        Log log = LogFactory.getLog(TeamRoadmapApplication.class);


        // load sellers
        log.info("Loading Sellers");
        sellerCSV = new ClassPathResource("static/csv/sellers.csv");
        try (Scanner scanner = new Scanner(new InputStreamReader(sellerCSV.getInputStream()))) {
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
                        true
                ));
            }
        }
        log.info("Sellers Loaded");

        // load customers
        log.info("Loading Customers");
        customerCSV = new ClassPathResource("static/csv/customers.csv");
        try (Scanner scanner = new Scanner(new InputStreamReader(customerCSV.getInputStream()))) {
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
                        true
                ));
            }
        }
        log.info("Customers Loaded");

        // load bundles
        log.info("Loading Bundles");
        bundleCSV = new ClassPathResource("static/csv/bundles.csv");
        try (Scanner scanner = new Scanner(new InputStreamReader(bundleCSV.getInputStream()))) {

            while (scanner.hasNextLine()) {
                List<String> bundle_info = getRecordFromLine(scanner.nextLine());


                // convert from text to array lists
                String sanitised_contents = bundle_info.get(2).replaceAll("[\\s\\[\\]']", "");
                String sanitised_allergens = bundle_info.get(3).replaceAll("[\\s\\[\\]']", "");

                ArrayList<String> contents = new ArrayList<>(Arrays.asList(sanitised_contents.split(",")));
                ArrayList<String> allergens = new ArrayList<>(Arrays.asList(sanitised_allergens.split(",")));

                // get relational components
                Seller seller = sellerRepository.findById(Integer.parseInt(bundle_info.get(0))).get();

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
                        Boolean.parseBoolean(bundle_info.get(9))
                ));
            }
        }
        log.info("Bundles Loaded");

        // load reservations
        log.info("Loading Reservations");
        reservationCSV = new ClassPathResource("static/csv/reservation.csv");
        try (Scanner scanner = new Scanner(new InputStreamReader(reservationCSV.getInputStream()))) {

            while (scanner.hasNextLine()) {
                List<String> reservation_info = getRecordFromLine(scanner.nextLine());

                // get relational components
                Bundle bundle = bundleRepository.findById(Integer.parseInt(reservation_info.get(0))).get();
                Customer customer = customerRepository.findById(Integer.parseInt(reservation_info.get(1))).get();
                Seller seller = sellerRepository.findById(Integer.parseInt(reservation_info.get(2))).get();

                // add to repo
                reservationRepository.save(new Reservation(
                        bundle,
                        customer,
                        seller,
                        LocalDateTime.parse(reservation_info.get(3)),
                        reservation_info.get(4),
                        Boolean.parseBoolean(reservation_info.get(5)),
                        Boolean.parseBoolean(reservation_info.get(6)),
                        reservation_info.get(7)
                ));
            }
        }
        log.info("Reservations Loaded");

    }


    @PreDestroy
    public void write_all_csvs() throws IOException {
        // set up logging
        Log log = LogFactory.getLog(TeamRoadmapApplication.class);

        // get all file writers
        System.out.println("Testing Testing 123");
        PrintWriter seller_writer = new PrintWriter(sellerCSV.getFile());
        PrintWriter customer_writer = new PrintWriter(customerCSV.getFile());
        PrintWriter bundle_writer = new PrintWriter(bundleCSV.getFile());
        PrintWriter reservation_writer = new PrintWriter(reservationCSV.getFile());

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
                    "%d&%s&%s&%s&%s&%f&%d&%d&%b&%b\n",
                    bundle.getSeller().getSellerID(),
                    bundle.getCategory(),
                    bundle.getContents().toString(),
                    bundle.getAllergens().toString(),
                    bundle.getTimeStamp().toString(),
                    bundle.getPrice(),
                    bundle.getDiscount(),
                    bundle.getPickUpWindow(),
                    bundle.getReserved(),
                    bundle.getExpired()
            );
        }
        bundle_writer.close();
        log.info("bundles written");


        // write all reservations
        log.info("writing reservations");
        for (Reservation reservation : reservationRepository.findAll()) {
            reservation_writer.printf(
                    "%d&%d&%d&%s&%s&%b&%b&%s\n",
                    reservation.getBundle().getPostingID(),
                    reservation.getCustomer().getCustomerID(),
                    reservation.getSeller().getSellerID(),
                    reservation.getTimeStamp().toString(),
                    reservation.getClaimCode(),
                    reservation.getNoShow(),
                    reservation.getCollected(),
                    reservation.getWeatherFlag()
            );
        }
        reservation_writer.close();
        log.info("reservations written");


        log.info("finished writing database to csv files");
    }
}
