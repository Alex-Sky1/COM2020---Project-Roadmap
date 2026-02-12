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
    private Long ID;
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
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private LocalDateTime steakLastUpdate;
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

    String generateClaimCode(){
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


    public Long getCustomerID(){return ID;}
    public void setCustomerID(Long customerID){this.ID = customerID;}

    public String getdName(){return dName;}
    public void setdName(String dName){this.dName = dName;}

    public String getfName(){return fName;}
    public void setfName(String fName){this.fName = fName;}

    public String getsName(){return sName;}
    public void setsName(String sName){this.sName = sName;}

    public String getAddress(){return address;}
    public void setAddress(String address){this.address = address;}

    public String getPostcode(){return postcode;}
    public void setPostcode(String postcode){this.postcode = postcode;}

    public String getCounty(){return county;}
    public void setCounty(String county){this.county = county;}

    public String getEmail(){return email;}
    public void setEmail(String email){this.email = email;}

    public String getPhone(){return phone;}
    public void setPhone(String phone){this.phone = phone;}

    public int getStreak(){return streak;}
    public void setStreak(int streak){this.streak = streak;}

    public ArrayList<Boolean> getBadges(){return badges;}
    public void setBadges(ArrayList<Boolean> badges){this.badges = badges;}

    public String getPassword(){return password;}
    public void setPassword(String password){this.password = password;}

    public LocalDateTime getSteakLastUpdate(){return steakLastUpdate;}
    public void setSteakLastUpdate(LocalDateTime lastUpdate){this.steakLastUpdate = lastUpdate;}

}
