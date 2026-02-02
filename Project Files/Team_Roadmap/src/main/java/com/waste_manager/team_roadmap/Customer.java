package com.waste_manager.team_roadmap;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

import jakarta.persistence.*;
import org.hibernate.id.IncrementGenerator;

@Entity
public class Customer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long customerID;
    @Column(nullable = false)
    private String displayName;
    @Column(nullable = false)
    private int streak;
    private ArrayList<Boolean> badges;

    public Customer(){};

    public Customer(long thisCustomerID, String thisDisplayName, int thisStreak, ArrayList<Boolean> thisBadges){

        this.customerID = thisCustomerID;
        this.displayName = thisDisplayName;
        this.streak = thisStreak;
        this.badges = thisBadges;
    }

    boolean login(String displayName, String password){
        return true;
    }
    boolean signup(String fName, String lName, String displayName, String email, String phone, String password, String address1, String postcode, String county){
        return true;
    }
    Reservation getReservation(){
        return null;
    }
    String generateClaimCode(int reservationID){return null;}
    void reportIssue(int reservationID, String type, String description){}
    ArrayList<Bundle> viewBundles(ArrayList<String> allergens, LocalDateTime time, ArrayList<Integer> location, String category){
        return null;
    }
    void reserveBundle(int postingID){}
    String customerNotification(){return null;}
    String getImpactSummary(){return null;}


    Long getCustomerID(){return customerID;}
    void setCustomerID(Long customerID){this.customerID = customerID;}

    String getDisplayName(){return displayName;}
    void setDisplayName(String displayName){this.displayName = displayName;}

    int getStreak(){return streak;}
    void setStreak(int streak){this.streak = streak;}

    ArrayList<Boolean> getBadges(){return badges;}
    void setBadges(ArrayList<Boolean> badges){this.badges = badges;}


}
