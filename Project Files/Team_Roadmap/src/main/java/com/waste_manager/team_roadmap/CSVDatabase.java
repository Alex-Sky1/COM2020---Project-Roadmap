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
        try (Scanner scanner = new Scanner(new ClassPathResource("static/csv/sellers.csv").getFile())) {
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
                        seller_info.get(8)
                ));
            }
        }
        log.info("Sellers Loaded");

        // load customers
        log.info("Loading Customers");
        try (Scanner scanner = new Scanner(new ClassPathResource("static/csv/customers.csv").getFile())) {
            while (scanner.hasNextLine()) {
                List<String> customer_info = getRecordFromLine(scanner.nextLine());

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
                        5,
                        new ArrayList<Boolean>()
                ));
            }
        }
        log.info("Customers Loaded");

        // load bundles
        log.info("Loading Bundles");
        try (Scanner scanner = new Scanner(new ClassPathResource("static/csv/bundles.csv").getFile())) {

            while (scanner.hasNextLine()) {
                List<String> bundle_info = getRecordFromLine(scanner.nextLine());


                // convert from text to array lists
                String sanitised_contents = bundle_info.get(2).replaceAll("[\\s\\[\\]']", "");
                String sanitised_allergens = bundle_info.get(3).replaceAll("[\\s\\[\\]']", "");

                ArrayList<String> contents = new ArrayList<>(Arrays.asList(sanitised_contents.split(",")));
                ArrayList<String> allergens = new ArrayList<>(Arrays.asList(sanitised_allergens.split(",")));

                // get relational components
                Seller seller = sellerRepository.findById(Integer.parseInt(bundle_info.getFirst()) + 1).get();

                // add to repo
                bundleRepository.save(new Bundle(
                        seller,
                        bundle_info.get(1),
                        contents,
                        allergens,
                        // 2026-02-17T18:25:43.014748
                        LocalDateTime.now(),
                        Float.parseFloat(bundle_info.get(4)),
                        Integer.parseInt(bundle_info.get(5)),
                        Integer.parseInt(bundle_info.get(6)),
                        Boolean.parseBoolean(bundle_info.get(7)),
                        Boolean.parseBoolean(bundle_info.get(8))
                ));
            }
        }
        log.info("Bundles Loaded");

        // load reservations
        log.info("Loading Reservations");
        try (Scanner scanner = new Scanner(new ClassPathResource("static/csv/reservation.csv").getFile())) {

            while (scanner.hasNextLine()) {
                List<String> reservation_info = getRecordFromLine(scanner.nextLine());

                // get relational components
                Bundle bundle = bundleRepository.findById(Integer.parseInt(reservation_info.getFirst()) + 1).get();
                Customer customer = customerRepository.findById(Integer.parseInt(reservation_info.get(1)) + 1).get();
                Seller seller = sellerRepository.findById(Integer.parseInt(reservation_info.get(2)) + 1).get();

                // add to repo
                reservationRepository.save(new Reservation(
                        bundle,
                        customer,
                        seller,
                        LocalDateTime.now(),
                        reservation_info.get(3),
                        Boolean.parseBoolean(reservation_info.get(4)),
                        Boolean.parseBoolean(reservation_info.get(5)),
                        reservation_info.get(6)
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
//        PrintWriter seller_writer = new PrintWriter(new FileWriter(new ClassPathResource("static/csv/seller.csv").getFile()));
//        PrintWriter customer_writer = new PrintWriter(new FileWriter(new ClassPathResource("static/csv/customer.csv").getFile()));
//        PrintWriter bundle_writer = new PrintWriter(new FileWriter(new ClassPathResource("static/csv/bundle.csv").getFile()));
//        PrintWriter reservation_writer = new PrintWriter(new FileWriter(new ClassPathResource("static/csv/reservation.csv").getFile()));

        // write all sellers
        log.info("writing sellers");
        for (Seller seller : sellerRepository.findAll()) {
//            seller_writer.printf(
//                    "%s%s%s%s%s%s%s%s%s",
//                    seller.getfName(),
//                    seller.getsName(),
//                    seller.getdName(),
//                    seller.getAddress(),
//                    seller.getPostcode(),
//                    seller.getCounty(),
//                    seller.getEmail(),
//                    seller.getPhone(),
//                    seller.getPassword()
//                );
        }
        log.info("sellers written");


        // write all customers
        log.info("writing customers");
        for (Customer customer : customerRepository.findAll()) {

        }
        log.info("customers written");


        // write all bundles
        log.info("writing bundles");
        for (Bundle bundle : bundleRepository.findAll()) {

        }
        log.info("bundles written");


        // write all reservations
        log.info("writing reservations");
        for (Reservation reservation : reservationRepository.findAll()) {

        }
        log.info("reservations written");

        // close writers
//        seller_writer.close();
//        customer_writer.close();
//        bundle_writer.close();
//        reservation_writer.close();
        log.info("finished writing database to csv files");
    }
}
