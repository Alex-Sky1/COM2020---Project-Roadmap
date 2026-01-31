package com.waste_manager.team_roadmap;
import java.time.LocalDateTime;

public class Customer {
    private int customerID;
    private String displayName;
    private int streak;
    private boolean[] badges;

    public Customer(){}

    boolean login(String displayName, String password){
        return true;
    }
    boolean signup(String fname, String sname, String displayName, String email, String phone, String password, String address1, String postcode, String county){
        return true;
    }
    Reservation getReservation(){
        return null;
    }
    String generateClaimCode(int reservationID){return null;}
    void reportIssue(int reservationID, String type, String description){}
    Bundle[] viewBundles(String[] allergens, LocalDateTime time, int[] location, String category){
        return null;
    }
    void reserveBundle(int postingID){}
    String customerNotification(){return null;}
    String getImpactSummary(){return null;}


    int getCustomerID(){return customerID;}
    void setCustomerID(int customerID){this.customerID = customerID;}

    String getDisplayName(){return displayName;}
    void setDisplayName(String displayName){this.displayName = displayName;}

    int getStreak(){return streak;}
    void setStreak(int streak){this.streak = streak;}

    boolean[] getBadges(){return badges;}
    void setBadges(boolean[] badges){this.badges = badges;}


}
