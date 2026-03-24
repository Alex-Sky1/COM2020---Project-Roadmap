package com.waste_manager.team_roadmap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

@Component
public class ModelTrainer {
    @Autowired
    BundleRepository br;
    @Autowired
    ReservationRepository rr;

    @EventListener(ApplicationReadyEvent.class)
    public void trainModel() {
        Forecast forecast = new Forecast(new ArrayList<>(br.findAll()), new ArrayList<>(rr.findAll()));
        try {
            System.out.println("doing");
            forecast.onStartUp();
            System.out.println("done");
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    private ArrayList<Bundle> loadTestBundles() throws IOException {
         ArrayList<Bundle> res = new ArrayList<>();
         Scanner scanner = new Scanner(new InputStreamReader(new ClassPathResource("static/csv/bundles_TESTING.csv").getInputStream()));
        while (scanner.hasNextLine()) {
            List<String> bundle_info = CSVDatabase.getRecordFromLine(scanner.nextLine());

            // convert from text to array lists
            String sanitised_contents = bundle_info.get(2).replaceAll("[\\s\\[\\]']", "");
            String sanitised_allergens = bundle_info.get(3).replaceAll("[\\s\\[\\]']", "");

            ArrayList<String> contents = new ArrayList<>(Arrays.asList(sanitised_contents.split(",")));
            ArrayList<String> allergens = new ArrayList<>(Arrays.asList(sanitised_allergens.split(",")));

            // add to repo
            res.add(new Bundle(
                    new Seller(),
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

        return res;
    }

    private ArrayList<Reservation> loadTestReservations(ArrayList<Bundle> bundles) throws IOException {
        ArrayList<Reservation> res = new ArrayList<>();
        Scanner scanner = new Scanner(new InputStreamReader(new ClassPathResource("static/csv/reservation_TESTING.csv").getInputStream()));

        while (scanner.hasNextLine()) {
            List<String> reservation_info = CSVDatabase.getRecordFromLine(scanner.nextLine());

            // get relational components
            Bundle bundle = bundles.get(Integer.parseInt(reservation_info.get(0)) - 1);

            // add to repo
            res.add(new Reservation(
                    bundle,
                    new Customer(),
                    new Seller(),
                    LocalDateTime.parse(reservation_info.get(3)),
                    reservation_info.get(4),
                    Boolean.parseBoolean(reservation_info.get(5)),
                    Boolean.parseBoolean(reservation_info.get(6))
            ));
        }

        return res;
    }
}
