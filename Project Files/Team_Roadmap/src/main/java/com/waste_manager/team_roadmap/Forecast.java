package com.waste_manager.team_roadmap;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;
import weka.classifiers.functions.LinearRegression;
import weka.classifiers.functions.Logistic;
import weka.core.*;
import weka.filters.Filter;
import weka.filters.supervised.attribute.NominalToBinary;
import weka.filters.unsupervised.attribute.Normalize;
import weka.filters.unsupervised.attribute.NumericToNominal;
import weka.filters.unsupervised.attribute.StringToNominal;

import static java.lang.String.format;

public class Forecast {


    private static LinearRegression model;
    private static LinearRegression model2;
    private static Instances table1;
    private static Instances table2;

    private static StringToNominal StringTonominal1;
    private static StringToNominal StringTonominal2;
    private static NominalToBinary nominalTobinary1;
    private static NominalToBinary nominalTobinary2;
    private static Normalize normalized1;
    private static Normalize normalized2;
    private static int numOfAttr = 7;

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

    //https://weka.sourceforge.io/doc.dev/
    //https://gist.github.com/knbknb/c7f75d8eaa5b50a7b6786ca5f0fedbfb
    public double prediction(Bundle bun,String type,boolean conf) throws Exception {
            return workAround(bun, model,type,conf);

    }

    private double workAround(Bundle bun, LinearRegression model,String type,boolean conf) throws Exception {
        double[] dat = new double[8];
        dat[0] = bun.getTimeStamp().getDayOfWeek().getValue();
        dat[1] = bun.getPickUpWindow();
        dat[2] = bun.getSeller().getSellerID();
        dat[3] = numberCatNum(bun.getCategory());
        dat[4] = numberweather(bun.getWeatherFlag());
        dat[5] = bun.getPrice();
        dat[6] = bun.getDiscount();
        dat[7] = 0;




        Instance newRow = null;
        if (type == "reservations") {
            newRow = new DenseInstance(table1.numAttributes());
            newRow.setDataset(table1);
            newRow.setValue(table1.attribute("Day"),dayString(dat[0]));
            newRow.setValue(table1.attribute("pickupWindow"),timeString(dat[1]));
            newRow.setValue(table1.attribute("seller"),SellerString(dat[2]));
            newRow.setValue(table1.attribute("category"),numberCatString(dat[3]));
            newRow.setValue(table1.attribute("weather"),numberweatherString(dat[4]));
            newRow.setValue(table1.attribute("price"),(dat[5]));
            newRow.setValue(table1.attribute("discount"),(dat[6]));

            StringTonominal1.input(newRow);
            Instance StringTonominalData = StringTonominal1.output();

            nominalTobinary1.input(StringTonominalData);
            Instance nominalTobinaryData = nominalTobinary1.output();

            normalized1.input(nominalTobinaryData);
            Instance normalizedData = normalized1.output();



            if (conf) {
                double hold = 0;
                double[] confi = model.distributionForInstance(normalizedData);
                for (double v : confi) {
                    if (v > hold) {
                        hold = v;
                    }
                }

                return hold;

            }
            else{
                return Math.max(0,(Math.ceil(model.classifyInstance(normalizedData))));
            }

        }
        else if (type == "noshow"){
            newRow = new DenseInstance(table2.numAttributes());
            newRow.setDataset(table2);
            newRow.setValue(table2.attribute("Day"),dayString(dat[0]));
            newRow.setValue(table2.attribute("pickupWindow"),timeString(dat[1]));
            newRow.setValue(table2.attribute("seller"),SellerString(dat[2]));
            newRow.setValue(table2.attribute("category"),numberCatString(dat[3]));
            newRow.setValue(table2.attribute("weather"),numberweatherString(dat[4]));
            newRow.setValue(table2.attribute("price"),dat[5]);
            newRow.setValue(table2.attribute("discount"),dat[6]);

            StringTonominal2.input(newRow);
            Instance StringTonominalData = StringTonominal2.output();

            nominalTobinary2.input(StringTonominalData);
            Instance nominalTobinaryData = nominalTobinary2.output();

            normalized2.input(nominalTobinaryData);
            Instance normalizedData = normalized2.output();



            if (conf) {
                double hold = 0;
                double[] confi = model2.distributionForInstance(normalizedData);
                for (double v : confi) {
                    if (v > hold) {
                        hold = v;
                    }
                }

                return hold;
            }
            else {
                return model2.classifyInstance(normalizedData);
            }
        }



        return (Math.ceil(model2.classifyInstance(newRow)));
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
            bundleArray[i][4] = numberCatNum(b.getCategory());
            bundleArray[i][5] = numberweather(b.getWeatherFlag());
            bundleArray[i][6] = b.getPrice();
            bundleArray[i][7] = b.getDiscount();
        }
        return bundleArray;
    }


    public Instances build_data(String type){
        ArrayList<ArrayList<Double>> hold = group();

        ArrayList<Attribute> attributes = new ArrayList<>();
        attributes.add(new Attribute("Day",(ArrayList<String>) null));
        attributes.add(new Attribute("pickupWindow",(ArrayList<String>) null));
        attributes.add(new Attribute("seller",(ArrayList<String>) null));
        attributes.add(new Attribute("category",(ArrayList<String>) null));
        attributes.add(new Attribute("weather",(ArrayList<String>) null));
        attributes.add(new Attribute("price"));
        attributes.add(new Attribute("discount"));
        if (type == "reservations") {
            attributes.add(new Attribute("reservations"));
        }
        else if (type == "noshow"){
            //attributes.add(new Attribute("noshow",(ArrayList<String>) null));
            attributes.add(new Attribute("noshow"));
        }


        assert hold != null;
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


            Instance t = new DenseInstance(1.0,newRow);
            t.setValue(data.attribute("Day"),timeString(newRow[0]));
            t.setValue(data.attribute("pickupWindow"),timeString(newRow[1]));
            t.setValue(data.attribute("seller"),SellerString(newRow[2]));
            t.setValue(data.attribute("category"),numberCatString(newRow[3]));
            t.setValue(data.attribute("weather"),numberweatherString(newRow[4]));

//            if(type == "noshow"){
//                t.setValue(data.attribute("noshow"),convetper(newRow[7]));
//            }
            data.add(t);


        }
        if (type == "reservations") {
            table1 = data;
        }
        else if (type == "noshow"){
            table2 = data;
        }
        return data;

    }

    public ArrayList<ArrayList<Double>> group() {
        double[][] use = data();
        int rows = bundleList.size();


        int attr = 7;
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
                    for (int k = 1; k < attr + 1; k++) {
                        if (doubles.get(k) == use[i][k]) {
                            count++;
                        }
                    }
                    if (count == attr) {
                        break;
                    }
                }
            }
            double[] reservations_noShow = backup(use[i][0], use[i]);
            if (grouped.isEmpty() || count != attr) {
                for (int a = 0; a < attr+1; a++) {
                    make.add(use[i][a]);
                }
                make.add(1.0);
                make.add(reservations_noShow[0]);
                make.add(reservations_noShow[1]);
                grouped.add(make);
            }
            else{
                grouped.get(hold).set(8, grouped.get(hold).get(8) + 1);
                grouped.get(hold).set(9,grouped.get(hold).get(9) + reservations_noShow[0]);
                grouped.get(hold).set(10,grouped.get(hold).get(10) + reservations_noShow[1]);
            }

            i++;

        }
        for (ArrayList<Double> doubles : grouped) {
            if (doubles.get(8) > 0 ) {
                doubles.set(10, doubles.get(10) / doubles.get(8));
            } else {
                doubles.set(10, 0.0);
            }


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
                reservations_noShow[0] = reservations_noShow[0] + 1.0;
                if(reservationList.get(hold).getNoShow()){
                    reservations_noShow[1] = reservations_noShow[1] +1.0;
                }

            }
            hold++;
        }
        return reservations_noShow;

    }


public void trainModel(String type) throws Exception {

        if (model == null) {
            if (type == "reservations") {
                Instances data = build_data("reservations");
                data.randomize(new java.util.Random(1));

                StringTonominal1 = new StringToNominal();
                StringTonominal1.setAttributeRange("first-last");
                StringTonominal1.setInputFormat(data);
                Instances StringTonominalData1 = Filter.useFilter(data, StringTonominal1);

                nominalTobinary1 = new NominalToBinary();
                nominalTobinary1.setInputFormat(StringTonominalData1);
                Instances nominalTobinaryData1 = Filter.useFilter(StringTonominalData1, nominalTobinary1);



                normalized1 = new Normalize();
                normalized1.setInputFormat(nominalTobinaryData1);
                Instances normalizedData1 = Filter.useFilter(nominalTobinaryData1, normalized1);



                model = new LinearRegression();
                model.setAttributeSelectionMethod(new SelectedTag(LinearRegression.SELECTION_NONE, LinearRegression.TAGS_SELECTION));
                model.buildClassifier(normalizedData1);
            }
        }
        if (model2 == null) {
            if (type == "noshow") {
                Instances data = build_data("noshow");


                data.randomize(new java.util.Random(1));

                StringTonominal2 = new StringToNominal();
                StringTonominal2.setAttributeRange("first-last");
                StringTonominal2.setInputFormat(data);
                Instances StringTonominalData2 = Filter.useFilter(data, StringTonominal2);

                nominalTobinary2 = new NominalToBinary();
                nominalTobinary2.setInputFormat(StringTonominalData2);
                Instances nominalTobinaryData2 = Filter.useFilter(StringTonominalData2, nominalTobinary2);



                normalized2 = new Normalize();
                normalized2.setInputFormat(nominalTobinaryData2);
                Instances normalizedData2 = Filter.useFilter(nominalTobinaryData2, normalized2);


                model2 = new LinearRegression();
                model.setAttributeSelectionMethod(new SelectedTag(LinearRegression.SELECTION_NONE, LinearRegression.TAGS_SELECTION));
                model2.buildClassifier(normalizedData2);

            }
        }
}


public void onStartUp() throws Exception {
    trainModel("reservations");
    trainModel("noshow");
}



    public int numberCatNum(String category){

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
    public String numberCatString(double category){

        switch ((int) category){
            case 1:
                return "Fish & Meat";
            case 2:
                return "Bakery";
            case 3:
                return "Snacks";
            case 4:
                return "Dairy";
            case 5:
                return "Fruit, Vegetables & Legumes";
            case 6:
                return "Groceries";
            default:
                return "Other";

        }
    }

    public String dayString(double category){

        switch ((int) category){
            case 1:
                return "Monday";
            case 2:
                return "Tuesday";
            case 3:
                return "Wednesday";
            case 4:
                return "Thursday";
            case 5:
                return "Friday";
            case 6:
                return "Saturday";
            default:
                return "Sunday";

        }
    }


    public String timeString(double time){
            return String.valueOf(time);
    }

    public String SellerString(double ID){
        return String.valueOf(ID);
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

    public String numberweatherString(double weatherFlag){
        switch ((int) weatherFlag){
            case 1:
                return "sunny";
            case 2:
                return "rainy";
            case 3:
                return "cloudy";
            default:
                return "unknown";
        }
    }
//
//    public String convetper(double noshow){
//        if (noshow >= 0.5){
//            return "1";
//        }
//        else{
//            return "2";
//        }
//    }

    public String createRecommendation(Bundle bundle) throws Exception {
        return createRecommendation(bundle, false);
    }

    public String createRecommendation(Bundle bundle, boolean returnRecommend) throws Exception {

        ArrayList<Bundle> bundles = bundleFromSelectSeller();
        ArrayList<Bundle> duplicateBundles = new ArrayList<>();
        StringBuilder returnString = new StringBuilder();


        for (int i = 0; i < bundles.size(); i++){

            if (bundle.hasSameContent(bundles.get(i))) {
                duplicateBundles.add(bundles.get(i));
            }
        }

        int recommendedNumber = (int) Math.ceil(prediction(bundle, "reservation", false) * (1 - prediction(bundle, "noshow", false)));

        returnString.append("The recommended number of bundles to post is")
                .append(recommendedNumber)
                .append(" instead of ").append(duplicateBundles.size());

        if (recommendedNumber == duplicateBundles.size()) {
            return "The amount of bundles posted is the right amount";
        }
        if (returnRecommend) {
            return format("%d %d",  recommendedNumber, duplicateBundles.size());
        }
        else {
            return returnString.toString();
        }
    }

    public String rationale(Bundle bundle) throws Exception {

        String[] rationaleStringList = createRecommendation(bundle, true).split(" ");

        int recommendedNumber = Integer.parseInt(rationaleStringList[0]);
        int duplicateBundles = Integer.parseInt(rationaleStringList[1]);

        if (recommendedNumber > duplicateBundles) {
            return "Demand for bundles indicate more bundles would be sold. More food will be saved";
        }
        else if (recommendedNumber == duplicateBundles){
            return "The amount of bundles posted is the right amount";
        }
        else {
            return "The data indicates you are overposting bundles, and not all of them will sell";
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