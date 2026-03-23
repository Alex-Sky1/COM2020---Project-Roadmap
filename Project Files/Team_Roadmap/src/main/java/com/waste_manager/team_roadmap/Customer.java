package com.waste_manager.team_roadmap;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Random;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import jakarta.persistence.*;
import java.util.regex.Pattern;

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
    private LocalDateTime streakLastUpdate;
    public Customer(){}

    public Customer(String fName, String sName, String dName, String address, String pcode, String county, String email, String phone, String password, int streak, ArrayList<Boolean> badges, boolean hashPassword) {
        this.fName = fName;
        this.sName = sName;
        this.dName = dName;
        this.address = address;
        this.postcode = pcode;
        this.county = county;
        this.email = email;
        this.phone = phone;
        if(hashPassword) {
            BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
            this.password = passwordEncoder.encode(password);
        }
        else {
            this.password = password;
        }
        this.streak = streak;
        this.badges = badges;
    }

    public String generateClaimCode() {

        String options = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYS0123456789";

        // Randomly select characters and build into string
        Random rand = new Random();
        StringBuilder builder = new StringBuilder();

        for(int i = 0; i < 6; i++){
            builder.append(options.charAt(rand.nextInt(62)));
        }
        return builder.toString();
    }
    //Password validation
    public boolean validatePassword(String password) {
        String regexPassword = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[!£$%^&*()_+{}~:@<>?|¬=#;,./]).{8,}$";
        return password.matches(regexPassword);
    }

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
    public void setPassword(String password){

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        this.password = passwordEncoder.encode(password);
    }

    public LocalDateTime getStreakLastUpdate(){return streakLastUpdate;}
    public void setStreakLastUpdate(LocalDateTime lastUpdate){this.streakLastUpdate = lastUpdate;}

}
