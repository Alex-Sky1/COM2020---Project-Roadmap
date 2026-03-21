package com.waste_manager.team_roadmap;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Objects;
import java.util.stream.Collectors;
import org.apache.commons.math3.stat.regression.OLSMultipleLinearRegression;
import weka.classifiers.functions.LinearRegression;
import weka.core.Attribute;
import weka.core.DenseInstance;
import weka.core.Instances;
import weka.core.stopwords.Null;
import weka.filters.supervised.attribute.NominalToBinary;

public class Forecast {


    private static LinearRegression model;
    private static LinearRegression model2;
    private static Instances table;
    private static Instances table2;

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

    public Forecast(ArrayList<Bundle> thisBundleList, ArrayList<Reservation> thisReservationList) {
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


//    public int prediction() {
//        OLSMultipleLinearRegression regression = new OLSMultipleLinearRegression();
//        double[][] x = data();
//        double[] y = y();
//
//        regression.newSampleData(y, x);
//        double[] beta = regression.estimateRegressionParameters();
//        double[] predBundle = new double[7];
//        predBundle[0] = this.preditBundle.getTimeStamp().getDayOfWeek().getValue();
//        predBundle[1] = this.preditBundle.getPickUpWindow();
//        predBundle[2] = this.preditBundle.getSeller().getSellerID();
//        predBundle[3] = numberCat(this.preditBundle.getCategory());
//        predBundle[4] = numberweather(this.preditBundle.getWeatherFlag());
//        predBundle[5] = this.preditBundle.getPrice();
//        predBundle[6] = this.preditBundle.getDiscount();
//
//        double predicted = beta[0];
//        for (int i = 0; i < predBundle.length; i++) {
//            predicted += beta[i + 1] * predBundle[i];
//        }
//
//        return Math.toIntExact(Math.round(predicted));
//    }

    //https://weka.sourceforge.io/doc.dev/
    //https://gist.github.com/knbknb/c7f75d8eaa5b50a7b6786ca5f0fedbfb
    public double prediction(Bundle bun,String type) {


        if (type == "reservations") {
            return workAround(bun, model);

        }
        else if (type == "noshow"){
            double hold = workAround(bun, model);
            return workAround(bun, model2)/hold;
        }

        return 0;
    }

    private double workAround(Bundle bun, LinearRegression model) {
        double[] dat = new double[8];
        dat[0] = bun.getTimeStamp().getDayOfWeek().getValue();
        dat[1] = bun.getPickUpWindow();
        dat[2] = bun.getSeller().getSellerID();
        dat[3] = numberCat(bun.getCategory());
        dat[4] = numberweather(bun.getWeatherFlag());
        dat[5] = bun.getPrice();
        dat[6] = bun.getDiscount();

        double[] coef = model.coefficients();

        double hold = 0.0;
        for (int i =0; i < dat.length;i++){
            hold += dat[i] * coef[i];
        }


        return (Math.round(hold));
    }


    public int seasonalNaive() {
        return seasonalNaive(this.forecastDate);
    }


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
            } else {
                searchDate = searchDate.minusDays(7);
            }

        }
        return -1; // If there are no valid previous bundles to use for a prediction, return -1 as an error
    }

    public int movingavg() {
        return movingavg(this.forecastDate, 24);
    }

    public int movingavg(LocalDateTime date, int hours) {
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
        if (counter == 0) {
            return -1;
        }
        return returnInt / counter;
    }

    public float MAE() {
        return MAE("seasonalNaive", 0);
    }

    public float MAE(String baseline, int hours) {

        ArrayList<Bundle> filteredBundleList = bundleFromSelectSeller();
        ArrayList<Reservation> filteredReservationList = searchReservationSeller(filteredBundleList);
        int number = 0;

        float mae = 0;
        LocalDateTime searchDate = this.forecastDate.minusDays(7);
        while ((searchDate.getDayOfWeek() != DayOfWeek.MONDAY)) {
            searchDate = searchDate.minusDays(1);
        }
        LocalDateTime hold = this.forecastDate;
        int returnInt = 0;


        while (searchDate.isBefore(hold)) {

            LocalDateTime check = searchDate;

            check = check.toLocalDate().atStartOfDay();

            for (int i = 0; i < 24; i++) {
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
                    int moving = movingavg(check, hours);
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
        if (number == 0) {
            return 1;
        }
        mae = mae / number;
        return mae;
    }


    //sort out data

    public double[][] data() {
        int rows = bundleList.size();
        int cols = 8;

        double[][] bundleArray = new double[rows][cols];

        for (int i = 0; i < rows; i++) {
            Bundle b = bundleList.get(i);
            bundleArray[i][0] = b.getPostingID();
            bundleArray[i][1] = b.getTimeStamp().getDayOfWeek().getValue();
            bundleArray[i][2] = b.getPickUpWindow();
            bundleArray[i][3] = b.getSeller().getSellerID();
            bundleArray[i][4] = numberCat(b.getCategory());
            bundleArray[i][5] = numberweather(b.getWeatherFlag());
            bundleArray[i][6] = b.getPrice();
            bundleArray[i][7] = b.getDiscount();
        }
        return bundleArray;
    }


    public Instances build_data(String type){

        ArrayList<ArrayList<Double>> hold = group();
        ArrayList<Attribute> attributes = new ArrayList<>();
        attributes.add(new Attribute("Day"));
        attributes.add(new Attribute("pickupWindow"));
        attributes.add(new Attribute("seller"));
        attributes.add(new Attribute("category"));
        attributes.add(new Attribute("weather"));
        attributes.add(new Attribute("price"));
        attributes.add(new Attribute("discount"));
        if (type == "reservations") {
            attributes.add(new Attribute("reservations"));
        }
        else if (type == "noshow"){
            attributes.add(new Attribute("noshow"));
        }



        Instances data = new Instances("data",attributes, hold.size());
        data.setClassIndex(data.numAttributes() -1);


        for(ArrayList<Double> row : hold){

            double[] newRow = new double[data.numAttributes()];

            newRow[0] = row.get(1);
            newRow[1] = row.get(2);
            newRow[2] = row.get(3);
            newRow[3] = row.get(4);
            newRow[4] = row.get(5);
            newRow[5] = row.get(6);
            newRow[6] = row.get(7);
            if (type == "reservations") {
                newRow[7] = row.get(9);
            }
            else if(type == "noshow"){
                newRow[7] = row.get(10);
            }

            data.add(new DenseInstance(1.0,newRow));
        }
        if (type == "reservations") {
            table = data;
        }
        else if (type == "noshow"){
            table2 = data;
        }
        return data;

    }

    public ArrayList<ArrayList<Double>> group() {
        double[][] use = data();
        int rows = bundleList.size();


        int hold = -1;
        int i = 0;
        int count = 0;
        ArrayList<ArrayList<Double>> grouped = new ArrayList<>();
        while (i < rows) {
            ArrayList<Double> make = new ArrayList<Double>();
            hold = -1;
            count = 0;
            if (!grouped.isEmpty()) {
                for (ArrayList<Double> doubles : grouped) {
                    count = 0;
                    hold++;
                    for (int k = 1; k < 8; k++) {
                        if (doubles.get(k) == use[i][k]) {
                            count++;
                        }
                    }
                    if (count == 7) {
                        break;
                    }
                }
            } if (grouped.isEmpty() || count != 7){
                for (int a = 0; a < 9; a++) {
                    if (a <= 7) {
                        make.add(use[i][a]);
                    } else {
                        make.add(1.0);
                        double[] reservations_noShow;
                        reservations_noShow = backup(make.get(0),use[i]);
                        make.add(reservations_noShow[0]);
                        make.add(reservations_noShow[1]);
                        break;
                    }
                }
                grouped.add(make);
            }
            if (count == 7) {
                grouped.get(hold).set(7, grouped.get(hold).get(7) + 1);

            }

            i++;

        }

        return grouped;


    }




    public double[] backup(double id,double[] use) {
        int hold = 0;
        double[] reservations_noShow = new double[2];
        reservations_noShow[0] = 0.0;
        reservations_noShow[1] = 0.0;


        while(hold < reservationList.size()){
            if (reservationList.get(hold).getBundle().getPostingID() == id){
                if(reservationList.get(hold).getCollected()){
                    reservations_noShow[0] = reservations_noShow[0] +1.0;
                }
                if(reservationList.get(hold).getNoShow()){
                    reservations_noShow[1] = reservations_noShow[1] +1.0;
                }
            }
        }
        return reservations_noShow;

    }


public void trainModel(String type) throws Exception {

        if (model == null ){
            if (type == "reservations") {
                Instances data = build_data("reservations");
                model = new LinearRegression();
                model.buildClassifier(data);
            }
        if (model2 == null){
            if (type == "noshow") {
                Instances data = build_data("noshow");
                model2 = new LinearRegression();
                model2.buildClassifier(data);
                }
            }
        }
}


public void onStartUp() throws Exception {
    trainModel("reservations");
    trainModel("noshow");
}



    public int numberCat(String category){
        switch (category){
            case "Fish & Meat":
                return 1;
            case "Bakery":
                return 2;
            case "Snacks":
                return 3;
            case "Dairy":
                return 4;
            case "Fruit, Vegetables & Legumes":
                return 5;
            case "Groceries":
                return 6;
            default:
                return 0;

        }
    }


    public int numberweather(String weatherFlag){
        switch (weatherFlag){
            case "sunny":
                return 1;
            case "rainy":
                return 2;
            case "cloudy":
                return 3;
            default:
                return 0;
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