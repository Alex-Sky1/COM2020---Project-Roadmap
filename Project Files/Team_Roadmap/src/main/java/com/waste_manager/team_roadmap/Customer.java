package com.waste_manager.team_roadmap;
import java.io.Serializable;
import java.time.LocalDateTime;
import jakarta.persistence.*;

@Entity
public class Customer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long customerID;

    @Column(nullable = false)
    private String displayName;
    @Column(nullable = false)
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


    Long getCustomerID(){return customerID;}
    void setCustomerID(Long customerID){this.customerID = customerID;}

    String getDisplayName(){return displayName;}
    void setDisplayName(String displayName){this.displayName = displayName;}

    int getStreak(){return streak;}
    void setStreak(int streak){this.streak = streak;}

    boolean[] getBadges(){return badges;}
    void setBadges(boolean[] badges){this.badges = badges;}


}
