package com.waste_manager.team_roadmap;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.util.ArrayList;
import java.io.Serializable;

@Entity
public class Seller implements Serializable {
    @Id
    @GeneratedValue
    private int sellerID;
    @Column(nullable = false)
    private String name;
    private ArrayList<Integer> location;
    private ArrayList<Integer> openingHours;
    @Column(nullable = false)
    private String contactStub;

    // public Seller(){};

    public Seller(int thisSellerID, String thisName, ArrayList<Integer> thisLocation, ArrayList<Integer> thisOpeningHours, String thisContactStub) {
        this.sellerID = thisSellerID;
        this.name = thisName;
        this.location = thisLocation;
        this.openingHours = thisOpeningHours;
        this.contactStub = thisContactStub;
    }

    public boolean login(String username, String password){return true;}
    public boolean signup(String fName, String lName, String businessName, String email, String phone, String password, String address1, String postcode, String county){return true;}
    public void createBundle(String category, String[] contents, float price, String[] allergens, int quantity, int discountLevel, int pickupWindow){}
    public ArrayList<Bundle> viewCurrentBundles(){return null;}
    public ArrayList<Bundle> viewAllBundles(){return null;}
    public Forecast viewForecast(){return null;}
    public boolean verifyClaimCode(String code){return true;}
    public void changeReservationStatus(){}
    public void issueResponse(int issueID, String Response, boolean fix){}
    public ArrayList<Integer> calculateSellThrough(){return null;}
    public int calculateWasteProxy(){return 0;}
    public ArrayList<Integer> calculatePricingEffectiveness(){return null;}
    public ArrayList<String> calculateOperationalInsights(){return null;}
    public void recordActionTaken(){}


    public int getSellerID(){return sellerID;}
    public void setSellerID(int sellerID){this.sellerID = sellerID;}

    public String getName(){return name;}
    public void setName(String name){this.name = name;}

    public ArrayList<Integer> getLocation(){return location;}
    public void setLocation(ArrayList<Integer> location){this.location = location;}

    public ArrayList<Integer> getOpeningHours(){return openingHours;}
    public void setOpeningHours(ArrayList<Integer> openingHours){this.openingHours = openingHours;}

    public String getContactStub(){return contactStub;}
    public void setContactStub(String contactStub){this.contactStub = contactStub;}

}
