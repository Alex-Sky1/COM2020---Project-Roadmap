package com.waste_manager.team_roadmap;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.io.Serializable;

@Entity
public class Seller implements Serializable {
    @Id
    @GeneratedValue
    private int ID;
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
    //private ArrayList<Integer> openingHours;
    @Column(nullable = false)
    private String email;
    @Column(nullable = false)
    private String phone;
    @Column(nullable = false)
    private String password;

    public Seller(){};

    public Seller(String fName, String sName, String dName, String addressLine1, String postcode, String county, String email, String phone, String password) {
        this.fName = fName;
        this.sName = sName;
        this.dName = dName;
        this.address = addressLine1;
        this.postcode = postcode;
        this.county = county;
        this.email = email;
        this.phone = phone;
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        this.password = passwordEncoder.encode(password);

    }

    public Seller(String fName, String sName, String dName, String addressLine1, String postcode, String county, String email, String phone, String password, boolean fromCSV) {
        this.fName = fName;
        this.sName = sName;
        this.dName = dName;
        this.address = addressLine1;
        this.postcode = postcode;
        this.county = county;
        this.email = email;
        this.phone = phone;
        this.password = password;

    }

    public int getSellerID() {return ID;}
    public void setSellerID(int sellerID) {this.ID = sellerID;}

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

    public String getPassword(){return password;}
    public void setPassword(String password){

        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        this.password = passwordEncoder.encode(password);
    }

}
