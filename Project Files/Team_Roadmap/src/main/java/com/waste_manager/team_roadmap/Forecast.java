package com.waste_manager.team_roadmap;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;
import org.apache.commons.math3.stat.regression.OLSMultipleLinearRegression;

public class Forecast {

    private LocalDateTime forecastDate;
    private int sellerID;
    private String category;
    private String weatherFlag;
    private ArrayList<Bundle> bundleList;
    private ArrayList<Reservation> reservationList;
    private float confidence;
    private String rationale;


    public Forecast(LocalDateTime thisForecastDate, int thisSellerID, String thisWeatherFlag, String thisCategory,
                    ArrayList<Bundle> thisBundleList, ArrayList<Reservation> thisReservationList) {

        this.forecastDate = thisForecastDate;
        this.sellerID = thisSellerID;
        this.weatherFlag = thisWeatherFlag;
        this.category = thisCategory;
        this.bundleList = thisBundleList;
        this.reservationList = thisReservationList;
    }

    // Return bundles that are from a specific seller
    public ArrayList<Bundle> bundleFromSelectSeller() {

        ArrayList<Bundle> a = new ArrayList<>();

        for (Bundle sellerBundles : this.bundleList) {

            if (sellerBundles.getSeller().getSellerID() == this.sellerID) {

                a.add(sellerBundles);
            }
        }
        return a;
    }

    // Search and return reservations that are of a particular seller
    public ArrayList<Reservation> searchReservationSeller(ArrayList<Bundle> sellerBundles) {

        ArrayList<Reservation> a = new ArrayList<>();

        for (Reservation sellerReservation : this.reservationList) {

            for (Bundle sellerBundle : sellerBundles) {

                if (sellerReservation.getBundle().getPostingID() == sellerBundle.getPostingID()) {

                    a.add(sellerReservation);
                    break;
                }
            }
        }
        return a;
    }


    // For if people want to understand how this works
    //https://www.geeksforgeeks.org/java/stream-filter-java-examples/   ,
    //https://www.baeldung.com/java-convert-collection-arraylist
    // Filter the reservations to return reservations made on a specific date
    public ArrayList<Reservation> filterReservationListDate(LocalDate dateSearched, ArrayList<Reservation> filteredReservationList) {

        return filteredReservationList.stream()
                .filter(reservation -> reservation.getTimeStamp()
                        .toLocalDate()
                        .equals(dateSearched))
                .collect(Collectors.toCollection(ArrayList::new));
    }


    public int prediction(){
        OLSMultipleLinearRegression regression = new OLSMultipleLinearRegression();
        double[][] x = data();
        double[] y = y();

        regression.newSampleData(y, x);
        double[] beta = regression.estimateRegressionParameters();

    }


    public int seasonalNaive() {return seasonalNaive(this.forecastDate);}


    public int seasonalNaive(LocalDateTime date) {

        ArrayList<Bundle> filteredBundleList = bundleFromSelectSeller();
        ArrayList<Reservation> filteredReservationList = searchReservationSeller(filteredBundleList);



        LocalDateTime searchDate = date; // The search date is the date used to provide the seasonal naive
        int returnInt = 0; // The return integer is the number of bundles that were reserved and picked up

        while (!(filteredBundleList.get(0).getTimeStamp().isAfter(searchDate))) {

            ArrayList<Reservation> dayReservationList = filterReservationListDate(searchDate.toLocalDate(), filteredReservationList);

            if (!dayReservationList.isEmpty()) {


                for (Reservation reservation : dayReservationList) {

                    if (reservation.getBundle().getPickUpWindow() == searchDate.getHour() && Objects.equals(reservation.getBundle().getCategory(), this.category)) {

                        if (!(reservation.getNoShow())) {
                            returnInt += 1;
                        }
                    }
                }
                return returnInt;
            }
            else {
                searchDate = searchDate.minusDays(7);
            }

        }
        return -1; // If there are no valid previous bundles to use for a prediction, return -1 as an error
    }
    public int movingavg(){
        return movingavg(this.forecastDate,24);
    }
    public int movingavg(LocalDateTime date,int hours) {
        ArrayList<Bundle> filteredBundleList = bundleFromSelectSeller();
        ArrayList<Reservation> filteredReservationList = searchReservationSeller(filteredBundleList);

        LocalDateTime searchDate = date.minusHours(1);

        int returnInt = 0;
        int counter = 0;

        while (counter < hours) {

            ArrayList<Reservation> dayReservationList = filterReservationListDate(searchDate.toLocalDate(), filteredReservationList);
            if (!dayReservationList.isEmpty()) {


                for (Reservation reservation : dayReservationList) {

                    if (reservation.getBundle().getPickUpWindow() == searchDate.getHour() && Objects.equals(reservation.getBundle().getCategory(), this.category)) {

                        if (!(reservation.getNoShow())) {
                            returnInt += 1;
                        }
                    }
                }
            }
            counter++;
            searchDate = searchDate.minusHours(1);

        }
        if (counter == 0){
            return -1;
        }
        return returnInt/counter;
    }

    public float MAE() {
        return MAE("seasonalNaive",0);
    }

    public float MAE(String baseline,int hours) {

        ArrayList<Bundle> filteredBundleList = bundleFromSelectSeller();
        ArrayList<Reservation> filteredReservationList = searchReservationSeller(filteredBundleList);
        int number = 0;

        float mae = 0;
        LocalDateTime searchDate = this.forecastDate.minusDays(7);
        while ((searchDate.getDayOfWeek() != DayOfWeek.MONDAY)){
            searchDate = searchDate.minusDays(1);
        }
        LocalDateTime hold = this.forecastDate;
        int returnInt = 0;


        while(searchDate.isBefore(hold)){

            LocalDateTime check = searchDate;

            check = check.toLocalDate().atStartOfDay();

            for(int i = 0;i < 24;i++) {
                ArrayList<Reservation> dayReservationList = filterReservationListDate(check.toLocalDate(), filteredReservationList);

                if (!dayReservationList.isEmpty()) {

                    for (Reservation reservation : dayReservationList) {

                        if (reservation.getBundle().getPickUpWindow() == check.getHour() && Objects.equals(reservation.getBundle().getCategory(), this.category)) {

                            if (!(reservation.getNoShow())) {
                                returnInt += 1;
                                System.out.println(returnInt);
                            }
                        }
                    }
                }

                if (baseline.equals("seasonalNaive")) {
                    int naive = seasonalNaive(check);
                    if (naive == -1) {
                        naive = 0;
                    }
                    mae += Math.abs(returnInt - naive);
                } else if (baseline.equals("movingavg")) {
                    int moving = movingavg(check,hours);
                    if (moving == -1) {
                        moving = 0;
                    }
                    mae += Math.abs(returnInt - moving);
                }
                System.out.println(mae);
                returnInt = 0;
                number += 1;
                check = check.plusHours(1);
            }
            searchDate = searchDate.plusDays(1);
        }
        if(number == 0) {
            return 1;
        }
        mae = mae / number;
        return mae;
    }

    //weights



    public double[][] data(){
        int rows = bundleList.size();
        int cols = 7; // number of fields you want to include in String[][]

        double[][] bundleArray = new double[rows][cols];

        for (int i = 0; i < rows; i++) {
            Bundle b = bundleList.get(i);
            bundleArray[i][0] = b.getTimeStamp().getDayOfWeek().getValue();
            bundleArray[i][1] = b.getPickUpWindow();
            bundleArray[i][2] = b.getSeller().getSellerID();
            bundleArray[i][3] = b.getCategory();
            bundleArray[i][4] = b.getWeatherFlag;
            bundleArray[i][5] = b.getPrice();
            bundleArray[i][6] = b.getDiscount();
        }
        return bundleArray;
    }

    public double[] y(){
        int rows = bundleList.size();

        double[] a = new double[rows];

        for (int i = 0; i < rows; i++) {
            Bundle b = bundleList.get(i);
            if(b.getReserved()){
                a[i] = 1;
            }
            else{
                a[i] = 0;
            }
        }

    }

    // Getters and Setters

    public LocalDateTime getForecastDate(){return forecastDate;}
    public void setForecastDate(LocalDateTime forecastDate){this.forecastDate = forecastDate;}

    public String getCategory(){return category;}
    public void setCategory(String category){this.category = category;}

    public int getSellerID(){return sellerID;}
    public void setSellerID(int sellerID){this.sellerID = sellerID;}

    public String getWeatherFlag(){return weatherFlag;}
    public void setWeatherFlag(String weatherFlag){this.weatherFlag = weatherFlag;}

    public ArrayList<Bundle> getBundleList(){return bundleList;}
    public void setBundleList(ArrayList<Bundle> bundleList){this.bundleList = bundleList;}

    public ArrayList<Reservation> getReservationList(){return reservationList;}
    public void setReservationList(ArrayList<Reservation> reservationList){this.reservationList = reservationList;}

    public float getConfidence(){return confidence;}
    public void setConfidence(float confidence){this.confidence = confidence;}

    public String getRationale(){return rationale;}
    public void setRationale(String rationale){this.rationale = rationale;}

}