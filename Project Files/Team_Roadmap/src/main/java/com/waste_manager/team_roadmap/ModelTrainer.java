package com.waste_manager.team_roadmap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

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
}
