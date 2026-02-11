package com.waste_manager.team_roadmap;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Forecast {

    private LocalDateTime forcastDate;
    private int sellerID;
    private String category;

    private String weatherFlag;

    private ArrayList<Bundle> bundleList;
    private ArrayList<Reservation> reservationList;
    private ArrayList<Integer> bundleReservations;
    private ArrayList<Integer> bundleNoShows;

    private float confidence;
    private String rationale;



    public Forecast(LocalDateTime thisForcastDate, int thisSellerID, String thisWeatherFlag,
                    ArrayList<Bundle> thisBundleList, ArrayList<Integer> thisBundleReservations,
                    ArrayList<Integer> thisBundleNoShows){

        this.forcastDate = thisForcastDate;
        this.sellerID = thisSellerID;
        this.weatherFlag = thisWeatherFlag;
        this.bundleList = thisBundleList;
        this.bundleReservations = thisBundleReservations;
        this.bundleNoShows = thisBundleNoShows;
    }

    public int calculatePrediction(){return 0;}
    public String createRecommendation(){return null;}
    public void calculateConfidence(){}
    public void calculateRationale(){}
    public int estimateDemand(String day, LocalDateTime time, String category){return 0;}


    private ArrayList<Bundle> bundleFromSelectSeller(){
        ArrayList<Bundle> a = new ArrayList<>();


        for(Bundle sellerBundles : this.bundleList){
            if(sellerBundles.getSeller().getSellerID() == this.sellerID){
                a.add(sellerBundles);
            }
        }
        return a;

    }

    private ArrayList<Reservation> reservationFromSelectSeller(ArrayList<Bundle> sellerBundles){
        ArrayList<Reservation> a = new ArrayList<>();


        for(Reservation sellerReservation : this.reservationList) {
            for (Bundle sellerBundle : sellerBundles) {
                if (sellerReservation.getBundle().getPostingID() == sellerBundle.getPostingID()) {
                    a.add(sellerReservation);
                    break;
                }
            }
        }
        return a;
    }

    private ArrayList<Reservation> searchReservationListDate(LocalDate dateSearched, ArrayList<Reservation> filteredReservationList) {

        List<Reservation> returnList = filteredReservationList.stream()
                .filter(reservation -> reservation.getTimeStamp().toLocalDate().equals(dateSearched))
                .toList();

        return new ArrayList<>(List.of((Reservation)returnList));
    }

    public int seasonalNaive(){
        ArrayList<Bundle> filteredBundleList = bundleFromSelectSeller();
        ArrayList<Reservation> filteredReservationList = reservationFromSelectSeller(filteredBundleList);

        LocalDateTime hold = this.forcastDate.minusDays(7);
        int returnInt = 0;

        while(bundleList.getFirst().timeStamp.isBefore(hold)){

            ArrayList<Reservation> dayReservationList = searchReservationListDate(hold.toLocalDate(),filteredReservationList);

            if(!dayReservationList.isEmpty()) {

                for (Reservation reservation : dayReservationList) {

                    if (reservation.getTimeStamp().getHour() == hold.getHour() && Objects.equals(reservation.getBundle().getCategory(), category)) {

                        if (!(reservation.getNoShow())) {
                            returnInt += 1;
                        }
                    }
                }
                return returnInt;
            }
            else {
                hold = hold.minusDays(7);
            }

        }

        return -1;
    }




    public LocalDateTime getforcastDate(){return forcastDate;}
    public void setTime(LocalDateTime time){this.forcastDate = time;}

    public String getCategory(){return category;}
    public void setCategory(String category){this.category = category;}

    public int getSellerID(){return sellerID;}
    public void setSellerID(int sellerID){this.sellerID = sellerID;}

    public String getWeatherFlag(){return weatherFlag;}
    public void setWeatherFlag(String weatherFlag){this.weatherFlag = weatherFlag;}

    public int getPostingID(){return postingID;}
    public void setPostingID(int postingID){this.postingID = postingID;}

    public float getConfidence(){return confidence;}
    public void setConfidence(float confidence){this.confidence = confidence;}

    public String getRationale(){return rationale;}
    public void setRationale(String rationale){this.rationale = rationale;}

}