package com.waste_manager.team_roadmap;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Random;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import jakarta.persistence.*;
import org.hibernate.id.IncrementGenerator;

@Entity
public class Customer implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long customerID;
    @Column(nullable = false)
    private String displayName;
    private String fname;
    private String sname;
    private String address;
    private String pcode;
    private String county;
    private String email;
    private String phone;
    @Column(nullable = false)
    private int streak;
    private ArrayList<Boolean> badges;
    private String password;
    public Customer(){};

    public Customer(String fname, String sname, String dname, String al1, String pcode, String county, String email, String phone, String password, int streak, ArrayList<Boolean> badges){
        this.fname = fname;
        this.sname = sname;
        this.displayName = dname;
        this.address = address;
        this.pcode = pcode;
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

    String getDisplayName(){return displayName;}
    void setDisplayName(String displayName){this.displayName = displayName;}

    String getFname(){return fname;}
    void setFname(String fname){this.fname = fname;}

    String getSname(){return sname;}
    void setSname(String sname){this.sname = sname;}

    String getAddress(){return address;}
    void setAddress(String address){this.address = address;}

    String getPcode(){return pcode;}
    void setPcode(String pcode){this.pcode = pcode;}

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
