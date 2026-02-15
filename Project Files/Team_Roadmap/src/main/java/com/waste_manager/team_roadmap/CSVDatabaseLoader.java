package com.waste_manager.team_roadmap;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class CSVDatabaseLoader {

    private static final String DELIMITER = "/|";

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
        // load sellers
        try (Scanner scanner = new Scanner(new ClassPathResource("resources/static/csv/sellers.csv").getFile())) {
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

        // load customers
        try (Scanner scanner = new Scanner(new ClassPathResource("resources/static/csv/customers.csv").getFile())) {
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

        // load bundles
        try (Scanner scanner = new Scanner(new ClassPathResource("resources/static/csv/bundles.csv").getFile())) {
            while (scanner.hasNextLine()) {
                List<String> bundle_info = getRecordFromLine(scanner.nextLine());

                // convert from text to array lists
                ArrayList<String> contents = new ArrayList<>();
                ArrayList<String> allergens = new ArrayList<>();

                bundleRepository.save(new Bundle(
                        sellerRepository.findById(Integer.parseInt(bundle_info.getFirst())).get(),
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

        // load reservations

    }


}
