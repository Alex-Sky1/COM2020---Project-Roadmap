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

    public Forecast(){}

    public int calculatePrediction(){return 0;}
    public String createRecommendation(){return null;}
    public void calculateConfidence(){}
    public void calculateRationale(){}
    public int estimateDemand(String day, LocalDateTime time, String category){return 0;}


    public DayOfWeek getDay(){return day;}
    public void setDay(DayOfWeek day){this.day = day;}

    public LocalDateTime getTime(){return time;}
    public void setTime(LocalDateTime time){this.time = time;}

    public int getSellerID(){return sellerID;}
    public void setSellerID(int sellerID){this.sellerID = sellerID;}

    public String getCategory(){return category;}
    public void setCategory(String category){this.category = category;}

    public float getPrice(){return price;}
    public void setPrice(float price){this.price = price;}

    public int getDiscount(){return discount;}
    public void setDiscount(int discount){this.discount = discount;}

    public String getWeatherFlag(){return weatherFlag;}
    public void setWeatherFlag(String weatherFlag){this.weatherFlag = weatherFlag;}

    public int getPostingID(){return postingID;}
    public void setPostingID(int postingID){this.postingID = postingID;}

    public int getObservedRes(){return observedRes;}
    public void setObservedRes(int observedRes){this.observedRes = observedRes;}

    public int getObservedNoShow(){return observedNoShow;}
    public void setObservedNoShow(int observedNoShow){this.observedNoShow = observedNoShow;}

    public int getPredictedRes(){return predictedRes;}
    public void setPredictedRes(int predictedRes){this.predictedRes = predictedRes;}

    public int getPredictedNoShow(){return predictedNoShow;}
    public void setPredictedNoShow(int predictedNoShow){this.predictedNoShow = predictedNoShow;}

    public float getConfidence(){return confidence;}
    public void setConfidence(float confidence){this.confidence = confidence;}

    public String getRationale(){return rationale;}
    public void setRationale(String rationale){this.rationale = rationale;}

}