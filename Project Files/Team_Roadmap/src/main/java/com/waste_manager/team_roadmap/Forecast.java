package com.waste_manager.team_roadmap;
import java.time.LocalDateTime;

public class Forecast {
    private DayOfWeek day;
    private LocalDateTime time;
    private int sellerID;
    private String category;
    private float price;
    private int discount;
    private String weatherFlag;
    private int postingID;

    //might not need?
    private int observedRes;
    private int observedNoShow;
    private int predictedRes;
    private int predictedNoShow;
    private float confidence;
    private String rationale;
}