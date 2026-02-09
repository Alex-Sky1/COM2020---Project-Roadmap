package com.waste_manager.team_roadmap;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Random;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import jakarta.persistence.*;

@Entity
public class Customer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long customerID;
    @Column(nullable = false)
    private String dName;
    @Column(nullable = false)
    private String fName;
    @Column(nullable = false)
    private String sName;
    @Column(nullable = false)
    private String address;
    @Column(nullable = false)
    private String postcode;
    @Column(nullable = false)
    private String county;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String phone;
    @Column(nullable = false)
    private int streak;
    private ArrayList<Boolean> badges;
    private String password;
    public Customer(){};

    public Customer(String fName, String sName, String dName, String address, String pcode, String county, String email, String phone, String password, int streak, ArrayList<Boolean> badges){
        this.fName = fName;
        this.sName = sName;
        this.dName = dName;
        this.address = address;
        this.postcode = pcode;
        this.county = county;
        this.email = email;
        this.phone = phone;
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        this.password = passwordEncoder.encode(password);
        this.streak = streak;
        this.badges = badges;
    }

    public boolean signup(String fName, String lName, String businessName, String email, String phone, String password, String address1, String postcode, String county) {
        return  false;
    }


    Reservation getReservation(){
        return null;
    }

    String generateClaimCode(int reservationID){
        String options = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYS0123456789";
        Random rand = new Random();
        StringBuilder builder = new StringBuilder();
        for(int i = 0; i < 6; i++){
            builder.append(options.charAt(rand.nextInt(62)));
        }
        return builder.toString();
    }
    void reportIssue(int reservationID, String type, String description, int postingID){
    }
    ArrayList<Bundle> viewBundles(ArrayList<String> allergens, LocalDateTime time, ArrayList<Integer> location, String category){
        return null;
    }
    void reserveBundle(int postingID){}
    String customerNotification(){return null;}
    String getImpactSummary(){return null;}


    Long getCustomerID(){return customerID;}
    void setCustomerID(Long customerID){this.customerID = customerID;}

    String getdName(){return dName;}
    void setdName(String dName){this.dName = dName;}

    String getfName(){return fName;}
    void setfName(String fName){this.fName = fName;}

    String getsName(){return sName;}
    void setsName(String sName){this.sName = sName;}

    String getAddress(){return address;}
    void setAddress(String address){this.address = address;}

    String getPostcode(){return postcode;}
    void setPostcode(String postcode){this.postcode = postcode;}

    String getCounty(){return county;}
    void setCounty(String county){this.county = county;}

    String getEmail(){return email;}
    void setEmail(String email){this.email = email;}

    String getPhone(){return phone;}
    void setPhone(String phone){this.phone = phone;}

    int getStreak(){return streak;}
    void setStreak(int streak){this.streak = streak;}

    ArrayList<Boolean> getBadges(){return badges;}
    void setBadges(ArrayList<Boolean> badges){this.badges = badges;}

    String getPassword(){return password;}
    void setPassword(String password){this.password = password;}

}
