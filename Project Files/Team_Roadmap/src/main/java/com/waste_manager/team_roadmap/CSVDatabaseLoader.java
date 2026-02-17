package com.waste_manager.team_roadmap;

import org.apache.juli.logging.Log;
import org.apache.juli.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

@Service
public class CSVDatabaseLoader {

    @Autowired
    private ResourceLoader resourceLoader;
    private static final String DELIMITER = "&";

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

    public static void read_all_csvs(SellerRepository sellerRepository, CustomerRepository customerRepository, BundleRepository bundleRepository, ReservationRepository reservationRepository) throws IOException {

        // set up logging
        Log log = LogFactory.getLog(TeamRoadmapApplication.class);


        // load sellers
        log.info("Loading Sellers");
        try (Scanner scanner = new Scanner(new ClassPathResource("static/csv/sellers.csv").getFile())) {
            while (scanner.hasNextLine()) {
                List<String> seller_info = getRecordFromLine(scanner.nextLine());

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


                Seller seller = sellerRepository.findById(Integer.parseInt(bundle_info.getFirst()) + 1).get();

                bundleRepository.save(new Bundle(
                        seller,
                        bundle_info.get(1),
                        contents,
                        allergens,
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

//                Bundle bundle, Customer customer, Seller seller, LocalDateTime thisTimeStamp,
//                        String thisClaimCode, boolean thisNoShow, boolean thisCollected, String thisWeatherFlag)

                // convert from text to array lists
                Bundle bundle = bundleRepository.findById(Integer.parseInt(reservation_info.getFirst()) + 1).get();
                Customer customer = customerRepository.findById(Integer.parseInt(reservation_info.get(1)) + 1).get();
                Seller seller = sellerRepository.findById(Integer.parseInt(reservation_info.get(2)) + 1).get();

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


}
